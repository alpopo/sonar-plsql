/*
 * Sonar PL/SQL Plugin (Community)
 * Copyright (C) 2015-2017 Felipe Zorzo
 * mailto:felipebzorzo AT gmail DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.plsqlopen.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plsqlopen.matchers.MethodMatcher;
import org.sonar.plugins.plsqlopen.api.PlSqlGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import com.sonar.sslr.api.AstNode;

@Rule(
    key = ToDateWithoutFormatCheck.CHECK_KEY,
    priority = Priority.MAJOR,
    tags = Tags.BUG
)
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class ToDateWithoutFormatCheck extends AbstractBaseCheck {
    public static final String CHECK_KEY = "ToDateWithoutFormat";

    @Override
    public void init() {
        subscribeTo(PlSqlGrammar.METHOD_CALL);
    }
    
    @Override
    public void visitNode(AstNode node) {
        MethodMatcher toDate = MethodMatcher.create().name("to_date").addParameter();
        
        if (toDate.matches(node)) {
            getPlSqlContext().createViolation(this, getLocalizedMessage(CHECK_KEY), node);
        }
    }

}
