/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.kyuubi.engine.flink.result;

import java.util.Objects;
import javax.annotation.Nullable;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.utils.LogicalTypeParser;
import org.apache.flink.util.Preconditions;

/** A column info represents a table column's structure with column name, column type. */
public class ColumnInfo {

  private String name;

  private String type;

  @Nullable private LogicalType logicalType;

  public ColumnInfo(String name, String type) {
    this.name = Preconditions.checkNotNull(name, "name must not be null");
    this.type = Preconditions.checkNotNull(type, "type must not be null");
  }

  public static ColumnInfo create(String name, LogicalType type) {
    return new ColumnInfo(name, type.toString());
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public LogicalType getLogicalType() {
    if (logicalType == null) {
      logicalType = LogicalTypeParser.parse(type);
    }
    return logicalType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ColumnInfo that = (ColumnInfo) o;
    return name.equals(that.name) && type.equals(that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type);
  }

  @Override
  public String toString() {
    return "ColumnInfo{" + "name='" + name + '\'' + ", type='" + type + '\'' + '}';
  }
}
