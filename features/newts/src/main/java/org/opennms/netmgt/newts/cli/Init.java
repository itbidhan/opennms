/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2006-2015 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2015 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.newts.cli;

import java.util.ServiceLoader;

import org.opennms.newts.cassandra.Schema;
import org.opennms.newts.cassandra.SchemaManager;

public class Init implements Command {

    private static ServiceLoader<Schema> s_schemas = ServiceLoader.load(Schema.class);

    @Override
    public void execute() throws Exception {
        String keyspace = System.getProperty("org.opennms.newts.config.keyspace", "newts");
        String hostname = System.getProperty("org.opennms.newts.config.hostname", "localhost");
        int port = Integer.getInteger("org.opennms.newts.config.port", 9042);

        System.out.println(String.format("Initializing the '%s' keyspaces on %s:%d", keyspace, hostname, port));
        try (SchemaManager m = new SchemaManager(keyspace, hostname, port)) {
            for (Schema s : s_schemas) {
                m.create(s, true);
            }
        }
        System.out.println("The keyspace was succesfully created.");
    }
}
