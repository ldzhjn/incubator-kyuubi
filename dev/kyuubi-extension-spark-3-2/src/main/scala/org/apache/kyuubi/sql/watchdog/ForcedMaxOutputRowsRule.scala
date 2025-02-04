/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.kyuubi.sql.watchdog

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.plans.logical.{Aggregate, LogicalPlan, WithCTE}

case class ForcedMaxOutputRowsRule(sparkSession: SparkSession) extends ForcedMaxOutputRowsBase {

  override protected def isChildAggregate(a: Aggregate): Boolean = false

  override protected def canInsertLimitInner(p: LogicalPlan): Boolean = p match {
    case WithCTE(plan, _) => this.canInsertLimitInner(plan)
    case plan: LogicalPlan => super.canInsertLimitInner(plan)
  }

  override protected def canInsertLimit(p: LogicalPlan, maxOutputRowsOpt: Option[Int]): Boolean = {
    p match {
      case WithCTE(plan, _) => this.canInsertLimit(plan, maxOutputRowsOpt)
      case _ => super.canInsertLimit(p, maxOutputRowsOpt)
    }
  }
}
