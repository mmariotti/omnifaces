/*
 * Copyright 2014 OmniFaces.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.omnifaces;

import javax.faces.webapp.FacesServlet;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.omnifaces.cdi.Eager;
import org.omnifaces.cdi.eager.EagerBeansRepository;
import org.omnifaces.component.output.Cache;
import org.omnifaces.component.output.cache.CacheInitializer;
import org.omnifaces.eventlistener.DefaultServletContextListener;
import org.omnifaces.facesviews.FacesViews;

/**
 * <p>
 * OmniFaces application listener. This runs when the servlet context is created and thus after the
 * {@link ApplicationInitializer} and the {@link FacesServlet}.
 * This performs the following tasks:
 * <ol>
 * <li>Add {@link FacesViews} mappings to FacesServlet.
 * <li>Load the {@link Cache} provider and register its filter.
 * <li>Instantiate {@link Eager} application scoped beans.
 * </ol>
 *
 * @author Bauke Scholtz
 * @since 2.0
 */
@WebListener
public class ApplicationListener extends DefaultServletContextListener {

	// Properties -----------------------------------------------------------------------------------------------------

	@Inject
	private EagerBeansRepository eagerBeansRepository;

	// Actions --------------------------------------------------------------------------------------------------------

	@Override
	public void contextInitialized(ServletContextEvent event) {
		FacesViews.addMappings(event.getServletContext());
		CacheInitializer.loadProviderAndRegisterFilter(event.getServletContext());
		eagerBeansRepository.instantiateApplicationScoped();
	}

}