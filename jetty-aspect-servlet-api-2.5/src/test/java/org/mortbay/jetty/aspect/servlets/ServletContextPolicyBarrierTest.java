package org.mortbay.jetty.aspect.servlets;
//========================================================================
//$Id:$
//Copyright 2011 Webtide, LLC
//------------------------------------------------------------------------
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//http://www.apache.org/licenses/LICENSE-2.0
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
//========================================================================

import java.security.AccessControlException;
import java.security.Policy;
import java.util.HashMap;

import org.eclipse.jetty.policy.JettyPolicy;
import org.eclipse.jetty.server.LocalConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.toolchain.test.MavenTestingUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServletContextPolicyBarrierTest
{

    private HashMap<String, String> evaluator = new HashMap<String, String>();
    private Server server;
    private LocalConnector connector;
    private ServletContextHandler context;
    
    @Before
    public void setUp() throws Exception
    {
        
        evaluator.put("jetty.home",MavenTestingUtils.getBaseURI().toASCIIString());
        evaluator.put("basedir",MavenTestingUtils.getBaseURI().toASCIIString());
        
        System.setSecurityManager(null);
        Policy.setPolicy(null);

        server = new Server();
        server.setSendServerVersion(false);

        connector = new LocalConnector();

        context = new ServletContextHandler();
        context.setContextPath("/context");
        context.setWelcomeFiles(new String[] {"index.html","index.jsp","index.htm"});

        server.setHandler(context);
        server.addConnector(connector);

        server.start();
    }
    
    @After
    public void destroy() throws Exception
    {
        server.stop();
        server.join();
        
        System.setSecurityManager(null);
        Policy.setPolicy(null);
    }
    
    private void testServletContextGetAttribute( String directory ) throws Exception
    {
        JettyPolicy ap = new JettyPolicy( directory, evaluator );
        ap.refresh();
                
        Policy.setPolicy(ap);
        System.setSecurityManager(new SecurityManager());
        
        ServletHolder defholder = context.addServlet(DefaultServlet.class,"/*");
        
        defholder.getServlet().getServletConfig().getServletContext().getAttribute("foo");

    }
    
    @Test (expected = AccessControlException.class)
    public void testServletContextGetAttributeNotAllowed() throws Exception
    {
        testServletContextGetAttribute(MavenTestingUtils.getTestResourceDir("servlet-context-test-1").getAbsolutePath());
    }
    
    @Test
    public void testServletContextGetAttributeAllowed() throws Exception
    {
        testServletContextGetAttribute(MavenTestingUtils.getTestResourceDir("servlet-context-test-2").getAbsolutePath());

    }
    
    private void testServletContextSetAttribute( String directory ) throws Exception
    {
        JettyPolicy ap = new JettyPolicy( directory, evaluator );
        ap.refresh();
                
        Policy.setPolicy(ap);
        System.setSecurityManager(new SecurityManager());
        
        ServletHolder defholder = context.addServlet(DefaultServlet.class,"/*");
        
        defholder.getServlet().getServletConfig().getServletContext().setAttribute("foo", "bar");

    }
    
    @Test (expected = AccessControlException.class)
    public void testServletContextSetAttributeNotAllowed() throws Exception
    {
        testServletContextSetAttribute(MavenTestingUtils.getTestResourceDir("servlet-context-test-1").getAbsolutePath());
    }
    
    @Test
    public void testServletContextSetAttributeAllowed() throws Exception
    {
        testServletContextSetAttribute(MavenTestingUtils.getTestResourceDir("servlet-context-test-2").getAbsolutePath());

    }
    
    private void testServletContextRemoveAttribute( String directory ) throws Exception
    {
        JettyPolicy ap = new JettyPolicy( directory, evaluator );
        ap.refresh();
                
        Policy.setPolicy(ap);
        System.setSecurityManager(new SecurityManager());
        
        ServletHolder defholder = context.addServlet(DefaultServlet.class,"/*");
        
        defholder.getServlet().getServletConfig().getServletContext().removeAttribute("foo");

    }
    
    @Test (expected = AccessControlException.class)
    public void testServletContextRemoveAttributeNotAllowed() throws Exception
    {
        testServletContextRemoveAttribute(MavenTestingUtils.getTestResourceDir("servlet-context-test-1").getAbsolutePath());
    }
    
    @Test
    public void testServletContextRemoveAttributeAllowed() throws Exception
    {
        testServletContextRemoveAttribute(MavenTestingUtils.getTestResourceDir("servlet-context-test-2").getAbsolutePath());

    }
    
    
    private void testServletContextGetInitParameter( String directory ) throws Exception
    {
        JettyPolicy ap = new JettyPolicy( directory, evaluator );
        ap.refresh();
                
        Policy.setPolicy(ap);
        System.setSecurityManager(new SecurityManager());
        
        ServletHolder defholder = context.addServlet(DefaultServlet.class,"/*");
        
        defholder.getServlet().getServletConfig().getServletContext().getInitParameter("foo");

    }
    
    @Test (expected = AccessControlException.class)
    public void testServletContextGetInitParameterNotAllowed() throws Exception
    {
        testServletContextGetInitParameter(MavenTestingUtils.getTestResourceDir("servlet-context-test-1").getAbsolutePath());
    }
    
    @Test
    public void testServletContextGetInitParameterAllowed() throws Exception
    {
        testServletContextGetInitParameter(MavenTestingUtils.getTestResourceDir("servlet-context-test-3").getAbsolutePath());

    }
    
    private void testServletContextGetRequestDispatcher( String directory ) throws Exception
    {
        JettyPolicy ap = new JettyPolicy( directory, evaluator );
        ap.refresh();
                
        Policy.setPolicy(ap);
        System.setSecurityManager(new SecurityManager());
        
        ServletHolder defholder = context.addServlet(DefaultServlet.class,"/*");
        
        defholder.getServlet().getServletConfig().getServletContext().getRequestDispatcher("foo");

    }
    
    @Test (expected = AccessControlException.class)
    public void testServletContextGetRequestDispatcherNotAllowed() throws Exception
    {
        testServletContextGetRequestDispatcher(MavenTestingUtils.getTestResourceDir("servlet-context-test-1").getAbsolutePath());
    }
    
    @Test
    public void testServletContextGetRequestDispatcherAllowed() throws Exception
    {
        testServletContextGetRequestDispatcher(MavenTestingUtils.getTestResourceDir("servlet-context-test-4").getAbsolutePath());

    }
    
    private void testServletContextGetNamedDispatcher( String directory ) throws Exception
    {
        JettyPolicy ap = new JettyPolicy( directory, evaluator );
        ap.refresh();
                
        Policy.setPolicy(ap);
        System.setSecurityManager(new SecurityManager());
        
        ServletHolder defholder = context.addServlet(DefaultServlet.class,"/*");
        
        defholder.getServlet().getServletConfig().getServletContext().getNamedDispatcher("foo");

    }
    
    @Test (expected = AccessControlException.class)
    public void testServletContextGetNamedDispatcherNotAllowed() throws Exception
    {
        testServletContextGetNamedDispatcher(MavenTestingUtils.getTestResourceDir("servlet-context-test-1").getAbsolutePath());
    }
    
    @Test
    public void testServletContextGetNamedDispatcherAllowed() throws Exception
    {
        testServletContextGetNamedDispatcher(MavenTestingUtils.getTestResourceDir("servlet-context-test-4").getAbsolutePath());

    }
    
    private void testServletContextGetContext( String directory ) throws Exception
    {
        JettyPolicy ap = new JettyPolicy( directory, evaluator );
        ap.refresh();
                
        Policy.setPolicy(ap);
        System.setSecurityManager(new SecurityManager());
        
        ServletHolder defholder = context.addServlet(DefaultServlet.class,"/*");
        
        defholder.getServlet().getServletConfig().getServletContext().getContext("foo");

    }
    
    @Test (expected = AccessControlException.class)
    public void testServletContextGetContextNotAllowed() throws Exception
    {
        testServletContextGetContext(MavenTestingUtils.getTestResourceDir("servlet-context-test-1").getAbsolutePath());
    }
    
    @Test
    public void testServletContextGetContextAllowed() throws Exception
    {
        testServletContextGetContext(MavenTestingUtils.getTestResourceDir("servlet-context-test-5").getAbsolutePath());

    }
    
    @Test (expected = ServletDeprecationException.class)
    public void testServletContextGetServletsBlocked() throws Exception
    {
        JettyPolicy ap = new JettyPolicy( MavenTestingUtils.getTestResourceDir("servlet-context-test-1").getAbsolutePath(), evaluator );
        ap.refresh();
                
        Policy.setPolicy(ap);
        System.setSecurityManager(new SecurityManager());
        
        ServletHolder defholder = context.addServlet(DefaultServlet.class,"/*");
        
        defholder.getServlet().getServletConfig().getServletContext().getServlets();
    }
    
    @Test (expected = ServletDeprecationException.class)
    public void testServletContextGetServletBlocked() throws Exception
    {
        JettyPolicy ap = new JettyPolicy( MavenTestingUtils.getTestResourceDir("servlet-context-test-1").getAbsolutePath(), evaluator );
        ap.refresh();
                
        Policy.setPolicy(ap);
        System.setSecurityManager(new SecurityManager());
        
        ServletHolder defholder = context.addServlet(DefaultServlet.class,"/*");
        
        defholder.getServlet().getServletConfig().getServletContext().getServlet("foo");
    }
    
    @Test (expected = ServletDeprecationException.class)
    public void testServletContextGetServletNamesBlocked() throws Exception
    {
        JettyPolicy ap = new JettyPolicy( MavenTestingUtils.getTestResourceDir("servlet-context-test-1").getAbsolutePath(), evaluator );
        ap.refresh();
                
        Policy.setPolicy(ap);
        System.setSecurityManager(new SecurityManager());
        
        ServletHolder defholder = context.addServlet(DefaultServlet.class,"/*");
        
        defholder.getServlet().getServletConfig().getServletContext().getServletNames();
    }
    
}
