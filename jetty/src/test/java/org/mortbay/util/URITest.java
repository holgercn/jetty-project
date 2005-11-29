// ========================================================================
// Copyright 2004 Mort Bay Consulting Pty. Ltd.
// ------------------------------------------------------------------------
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at 
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ========================================================================

package org.mortbay.util;

import junit.framework.TestSuite;


/* ------------------------------------------------------------ */
/** Util meta Tests.
 * @author Greg Wilkins (gregw)
 */
public class URITest extends junit.framework.TestCase
{
    public URITest(String name)
    {
      super(name);
    }
    
    public static junit.framework.Test suite() {
        TestSuite suite = new TestSuite(URITest.class);
        return suite;                  
    }

    /* ------------------------------------------------------------ */
    /** main.
     */
    public static void main(String[] args)
    {
      junit.textui.TestRunner.run(suite());
    }    
    
    /* ------------------------------------------------------------ */
    public void testURI()
    {
        URIUtil uri;

        // test basic encode/decode
        StringBuffer buf = new StringBuffer();
        URIUtil.encodeString(buf,"foo%23;,:=bar",";,=");
        assertEquals("foo%23;,:=bar",URIUtil.decodePath(buf.toString()));


        assertEquals("null+null", URIUtil.addPaths(null,null),null);
        assertEquals("null+", URIUtil.addPaths(null,""),null);
        assertEquals("null+bbb", URIUtil.addPaths(null,"bbb"),"bbb");
        assertEquals("null+/", URIUtil.addPaths(null,"/"),"/");
        assertEquals("null+/bbb", URIUtil.addPaths(null,"/bbb"),"/bbb");
        
        assertEquals("+null", URIUtil.addPaths("",null),"");
        assertEquals("+", URIUtil.addPaths("",""),"");
        assertEquals("+bbb", URIUtil.addPaths("","bbb"),"bbb");
        assertEquals("+/", URIUtil.addPaths("","/"),"/");
        assertEquals("+/bbb", URIUtil.addPaths("","/bbb"),"/bbb");
        
        assertEquals("aaa+null", URIUtil.addPaths("aaa",null),"aaa");
        assertEquals("aaa+", URIUtil.addPaths("aaa",""),"aaa");
        assertEquals("aaa+bbb", URIUtil.addPaths("aaa","bbb"),"aaa/bbb");
        assertEquals("aaa+/", URIUtil.addPaths("aaa","/"),"aaa/");
        assertEquals("aaa+/bbb", URIUtil.addPaths("aaa","/bbb"),"aaa/bbb");
        
        assertEquals("/+null", URIUtil.addPaths("/",null),"/");
        assertEquals("/+", URIUtil.addPaths("/",""),"/");
        assertEquals("/+bbb", URIUtil.addPaths("/","bbb"),"/bbb");
        assertEquals("/+/", URIUtil.addPaths("/","/"),"/");
        assertEquals("/+/bbb", URIUtil.addPaths("/","/bbb"),"/bbb");
        
        assertEquals("aaa/+null", URIUtil.addPaths("aaa/",null),"aaa/");
        assertEquals("aaa/+", URIUtil.addPaths("aaa/",""),"aaa/");
        assertEquals("aaa/+bbb", URIUtil.addPaths("aaa/","bbb"),"aaa/bbb");
        assertEquals("aaa/+/", URIUtil.addPaths("aaa/","/"),"aaa/");
        assertEquals("aaa/+/bbb", URIUtil.addPaths("aaa/","/bbb"),"aaa/bbb");
        
        assertEquals(";JS+null", URIUtil.addPaths(";JS",null),";JS");
        assertEquals(";JS+", URIUtil.addPaths(";JS",""),";JS");
        assertEquals(";JS+bbb", URIUtil.addPaths(";JS","bbb"),"bbb;JS");
        assertEquals(";JS+/", URIUtil.addPaths(";JS","/"),"/;JS");
        assertEquals(";JS+/bbb", URIUtil.addPaths(";JS","/bbb"),"/bbb;JS");
        
        assertEquals("aaa;JS+null", URIUtil.addPaths("aaa;JS",null),"aaa;JS");
        assertEquals("aaa;JS+", URIUtil.addPaths("aaa;JS",""),"aaa;JS");
        assertEquals("aaa;JS+bbb", URIUtil.addPaths("aaa;JS","bbb"),"aaa/bbb;JS");
        assertEquals("aaa;JS+/", URIUtil.addPaths("aaa;JS","/"),"aaa/;JS");
        assertEquals("aaa;JS+/bbb", URIUtil.addPaths("aaa;JS","/bbb"),"aaa/bbb;JS");
        
        assertEquals("aaa;JS+null", URIUtil.addPaths("aaa/;JS",null),"aaa/;JS");
        assertEquals("aaa;JS+", URIUtil.addPaths("aaa/;JS",""),"aaa/;JS");
        assertEquals("aaa;JS+bbb", URIUtil.addPaths("aaa/;JS","bbb"),"aaa/bbb;JS");
        assertEquals("aaa;JS+/", URIUtil.addPaths("aaa/;JS","/"),"aaa/;JS");
        assertEquals("aaa;JS+/bbb", URIUtil.addPaths("aaa/;JS","/bbb"),"aaa/bbb;JS");
        
        assertEquals("?A=1+null", URIUtil.addPaths("?A=1",null),"?A=1");
        assertEquals("?A=1+", URIUtil.addPaths("?A=1",""),"?A=1");
        assertEquals("?A=1+bbb", URIUtil.addPaths("?A=1","bbb"),"bbb?A=1");
        assertEquals("?A=1+/", URIUtil.addPaths("?A=1","/"),"/?A=1");
        assertEquals("?A=1+/bbb", URIUtil.addPaths("?A=1","/bbb"),"/bbb?A=1");
        
        assertEquals("aaa?A=1+null", URIUtil.addPaths("aaa?A=1",null),"aaa?A=1");
        assertEquals("aaa?A=1+", URIUtil.addPaths("aaa?A=1",""),"aaa?A=1");
        assertEquals("aaa?A=1+bbb", URIUtil.addPaths("aaa?A=1","bbb"),"aaa/bbb?A=1");
        assertEquals("aaa?A=1+/", URIUtil.addPaths("aaa?A=1","/"),"aaa/?A=1");
        assertEquals("aaa?A=1+/bbb", URIUtil.addPaths("aaa?A=1","/bbb"),"aaa/bbb?A=1");
        
        assertEquals("aaa?A=1+null", URIUtil.addPaths("aaa/?A=1",null),"aaa/?A=1");
        assertEquals("aaa?A=1+", URIUtil.addPaths("aaa/?A=1",""),"aaa/?A=1");
        assertEquals("aaa?A=1+bbb", URIUtil.addPaths("aaa/?A=1","bbb"),"aaa/bbb?A=1");
        assertEquals("aaa?A=1+/", URIUtil.addPaths("aaa/?A=1","/"),"aaa/?A=1");
        assertEquals("aaa?A=1+/bbb", URIUtil.addPaths("aaa/?A=1","/bbb"),"aaa/bbb?A=1");
        
        assertEquals(";JS?A=1+null", URIUtil.addPaths(";JS?A=1",null),";JS?A=1");
        assertEquals(";JS?A=1+", URIUtil.addPaths(";JS?A=1",""),";JS?A=1");
        assertEquals(";JS?A=1+bbb", URIUtil.addPaths(";JS?A=1","bbb"),"bbb;JS?A=1");
        assertEquals(";JS?A=1+/", URIUtil.addPaths(";JS?A=1","/"),"/;JS?A=1");
        assertEquals(";JS?A=1+/bbb", URIUtil.addPaths(";JS?A=1","/bbb"),"/bbb;JS?A=1");
        
        assertEquals("aaa;JS?A=1+null", URIUtil.addPaths("aaa;JS?A=1",null),"aaa;JS?A=1");
        assertEquals("aaa;JS?A=1+", URIUtil.addPaths("aaa;JS?A=1",""),"aaa;JS?A=1");
        assertEquals("aaa;JS?A=1+bbb", URIUtil.addPaths("aaa;JS?A=1","bbb"),"aaa/bbb;JS?A=1");
        assertEquals("aaa;JS?A=1+/", URIUtil.addPaths("aaa;JS?A=1","/"),"aaa/;JS?A=1");
        assertEquals("aaa;JS?A=1+/bbb", URIUtil.addPaths("aaa;JS?A=1","/bbb"),"aaa/bbb;JS?A=1");
        
        assertEquals("aaa;JS?A=1+null", URIUtil.addPaths("aaa/;JS?A=1",null),"aaa/;JS?A=1");
        assertEquals("aaa;JS?A=1+", URIUtil.addPaths("aaa/;JS?A=1",""),"aaa/;JS?A=1");
        assertEquals("aaa;JS?A=1+bbb", URIUtil.addPaths("aaa/;JS?A=1","bbb"),"aaa/bbb;JS?A=1");
        assertEquals("aaa;JS?A=1+/", URIUtil.addPaths("aaa/;JS?A=1","/"),"aaa/;JS?A=1");
        assertEquals("aaa;JS?A=1+/bbb", URIUtil.addPaths("aaa/;JS?A=1","/bbb"),"aaa/bbb;JS?A=1");

        assertEquals("parent /aaa/bbb/","/aaa/", URIUtil.parentPath("/aaa/bbb/"));
        assertEquals("parent /aaa/bbb","/aaa/", URIUtil.parentPath("/aaa/bbb"));
        assertEquals("parent /aaa/","/", URIUtil.parentPath("/aaa/"));
        assertEquals("parent /aaa","/", URIUtil.parentPath("/aaa"));
        assertEquals("parent /",null, URIUtil.parentPath("/"));
        assertEquals("parent null",null, URIUtil.parentPath(null));

        String[][] canonical = 
        {
            {"/aaa/bbb/","/aaa/bbb/"},
            {"/aaa//bbb/","/aaa/bbb/"},
            {"/aaa///bbb/","/aaa/bbb/"},
            {"/aaa/./bbb/","/aaa/bbb/"},
            {"/aaa/../bbb/","/bbb/"},
            {"/aaa/./../bbb/","/bbb/"},
            {"/aaa/bbb/ccc/../../ddd/","/aaa/ddd/"},
            {"./bbb/","bbb/"},
            {"./aaa/../bbb/","bbb/"},
            {"./",""},
            {".//",""},
            {".///",""},
            {"/.","/"},
            {"//.","/"},
            {"///.","/"},
            {"/","/"},
            {"aaa/bbb","aaa/bbb"},
            {"aaa/","aaa/"},
            {"aaa","aaa"},
            {"/aaa/bbb","/aaa/bbb"},
            {"/aaa//bbb","/aaa/bbb"},
            {"/aaa/./bbb","/aaa/bbb"},
            {"/aaa/../bbb","/bbb"},
            {"/aaa/./../bbb","/bbb"},
            {"./bbb","bbb"},
            {"./aaa/../bbb","bbb"},
            {"aaa/bbb/..","aaa/"},
            {"aaa/bbb/../","aaa/"},
            {"./",""},
            {".",""},
            {"",""},
            {"..",null},
            {"./..",null},
            {"aaa/../..",null},
            {"/foo/bar/../../..",null},
            {"/../foo",null},
            {"/foo/.","/foo/"},
            {"a","a"},
            {"a/","a/"},
            {"a/.","a/"},
            {"a/..",""},
            {"a/../..",null},
            {"/foo/../bar//","/bar/"}
        };

        for (int t=0;t<canonical.length;t++)
            assertEquals( "canonical "+canonical[t][0],
                          URIUtil.canonicalPath(canonical[t][0]),
                          canonical[t][1]
                          );
        
    }

}
