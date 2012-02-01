/*
 * Copyright (c) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.eclipse.jetty.npn;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;

public class NextProtoNego
{
    public static boolean debug = false;

    private static Map<Object, Provider> objects = Collections.synchronizedMap(new WeakHashMap<Object, Provider>());

    private NextProtoNego()
    {
    }

    public static void put(SSLSocket socket, Provider provider)
    {
        objects.put(socket, provider);
    }

    public static Provider get(SSLSocket socket)
    {
        return objects.get(socket);
    }

    public static void put(SSLEngine engine, Provider provider)
    {
        objects.put(engine, provider);
    }

    public static Provider get(SSLEngine engine)
    {
        return objects.get(engine);
    }

    public interface Provider
    {
    }

    public interface ClientProvider extends Provider
    {
        /**
         * <p>Callback invoked to let the implementation know whether an
         * empty NPN extension should be added to a ClientHello SSL message.</p>
         *
         * @return true to add the NPN extension, false otherwise
         */
        public boolean supports();

        /**
         * <p>Callback invoked to let the application select a protocol
         * among the ones sent by the server.</p>
         * <p>This callback is always invoked; the protocol list will be null
         * if the server does not support NPN, or will contain the protocols
         * supported by the server.</p>
         *
         * @param protocols the protocols sent by the server, or null if the
         * server does not support NPN
         * @return the protocol selected by the application, or null if the
         * NextProtocol SSL message should not be sent to the server
         */
        public String selectProtocol(List<String> protocols);
    }

    public interface ServerProvider extends Provider
    {
        /**
         * <p>Callback invoked to let the implementation know the list
         * of protocols that should be added to an NPN extension in a
         * ServerHello SSL message.</p>
         * <p>This callback is invoked only if the client sent a NPN extension.</p>
         *
         * @return the list of protocols, or null if no NPN extension
         * should be sent to the client
         */
        public List<String> protocols();

        /**
         * <p>Callback invoked to let the application know the protocol selected
         * by the client.</p>
         * <p>This callback is invoked only if the client sent a NextProtocol SSL message.</p>
         *
         * @param protocol the selected protocol
         */
        public void protocolSelected(String protocol);
    }
}
