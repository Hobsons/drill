/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.drill.exec.planner.sql;

import org.apache.drill.BaseTestQuery;
import org.apache.drill.exec.planner.physical.PlannerSettings;
import org.junit.Test;

public class TestSqlConverter extends BaseTestQuery {

  @Test
  public void testAnsiQuotedIdentifiers() throws Exception {
    test("select 1 from cp.`/store/text/classpath_storage_csv_test.csv` limit 1");
    errorMsgTestHelper("select 1 from cp.\"/store/text/classpath_storage_csv_test.csv\" limit 1",
        "PARSE ERROR: Encountered \". \\\"\"");
    try {
      test(String.format("ALTER SESSION SET `%s` = true", PlannerSettings.ANSI_QUOTES_KEY));
      test("select 1 from cp.\"/store/text/classpath_storage_csv_test.csv\" limit 1");
      test("select 1 from cp.`/store/text/classpath_storage_csv_test.csv` limit 1");
      test("select 1 from cp.`/store/text/classpath_storage_csv_test.csv` limit 1");
      test("select 1 from cp.\"/store/text/classpath_storage_csv_test.csv\" limit 1");
    } finally {
      test(String.format("ALTER SESSION RESET \"%s\"", PlannerSettings.ANSI_QUOTES_KEY));
    }
  }

}
