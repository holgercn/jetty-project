//========================================================================
//$Id$
//Copyright 2000-2009 Mort Bay Consulting Pty. Ltd.
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

package org.mortbay.jetty.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.eclipse.jetty.util.Scanner;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlConfiguration;

/**
 * <p>
 *  This goal is used to assemble your webapp into a war and automatically deploy it to Jetty.
 *  </p>
 *  <p>
 *  Once invoked, the plugin can be configured to run continuously, scanning for changes in the project and to the
 *  war file and automatically performing a 
 *  hot redeploy when necessary. 
 *  </p>
 *  <p>
 *  You may also specify the location of a jetty.xml file whose contents will be applied before any plugin configuration.
 *  This can be used, for example, to deploy a static webapp that is not part of your maven build. 
 *  </p>
 *  <p>
 *  There is a <a href="run-war-mojo.html">reference guide</a> to the configuration parameters for this plugin, and more detailed information
 *  with examples in the <a href="http://docs.codehaus.org/display/JETTY/Maven+Jetty+Plugin/">Configuration Guide</a>.
 *  </p>
 * 
 * @goal run-war
 * @requiresDependencyResolution compile+runtime
 * @execute phase="package"
 * @description Runs jetty on a war file
 *
 */
public class JettyRunWarMojo extends AbstractJettyMojo
{

    /**
     * The location of the war file.
     * @parameter expression="${project.build.directory}/${project.build.finalName}.war"
     * @required
     */
    private File war;


    
    /**
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        super.execute();  
    }



    public void configureWebApplication () throws Exception
    {
        super.configureWebApplication();
        
        webApp.setWar(war.getCanonicalPath());
    }
 


    /**
     * @see org.mortbay.jetty.plugin.AbstractJettyMojo#checkPomConfiguration()
     */
    public void checkPomConfiguration() throws MojoExecutionException
    {
       return;        
    }



    /* (non-Javadoc)
     * @see org.eclipse.jetty.server.plugin.AbstractJettyMojo#configureScanner()
     */
    public void configureScanner() throws MojoExecutionException
    {
        final ArrayList scanList = new ArrayList();
        scanList.add(getProject().getFile());
        scanList.add(war);
        setScanList(scanList);
        
        ArrayList listeners = new ArrayList();
        listeners.add(new Scanner.BulkListener()
        {
            public void filesChanged(List changes)
            {
                try
                {
                    boolean reconfigure = changes.contains(getProject().getFile().getCanonicalPath());
                    restartWebApp(reconfigure);
                }
                catch (Exception e)
                {
                    getLog().error("Error reconfiguring/restarting webapp after change in watched files",e);
                }
            }
        });
        setScannerListeners(listeners);        
    }


    public void restartWebApp(boolean reconfigureScanner) throws Exception 
    {
        getLog().info("Restarting webapp ...");
        getLog().debug("Stopping webapp ...");
        webApp.stop();
        getLog().debug("Reconfiguring webapp ...");

        checkPomConfiguration();

        // check if we need to reconfigure the scanner,
        // which is if the pom changes
        if (reconfigureScanner)
        {
            getLog().info("Reconfiguring scanner after change to pom.xml ...");
            ArrayList scanList = getScanList();
            scanList.clear();
            scanList.add(getProject().getFile());
            scanList.add(war);
            setScanList(scanList);
            getScanner().setScanDirs(scanList);
        }

        getLog().debug("Restarting webapp ...");
        webApp.start();
        getLog().info("Restart completed.");
    }

}
