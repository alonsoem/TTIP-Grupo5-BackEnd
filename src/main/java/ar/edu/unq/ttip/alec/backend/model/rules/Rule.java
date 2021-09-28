/*
 * The MIT License
 *
 *  Copyright (c) 2021, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package ar.edu.unq.ttip.alec.backend.model.rules;

import ar.edu.unq.ttip.alec.backend.model.Apartado;
import ar.edu.unq.ttip.alec.backend.model.TaxResult;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;

import java.io.FileReader;
import java.math.BigDecimal;

public class Rule {

    public TaxResult calculateWith(BigDecimal amount, Apartado apartado){

        RuleResult result = new RuleResult();

        //create a person instance (fact)

        Facts facts = new Facts();
        facts.put("apartado", apartado);
        facts.put("apartadoA", Apartado.APARTADOA);
        facts.put("amount", amount);
        facts.put("result", result);


        MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
        String fileName = "src/main/java/ar/edu/unq/ttip/alec/backend/model/rules/pais-rule.yml";
        Rules rules = new Rules();
        try {
            org.jeasy.rules.api.Rule ruleApartadoA = ruleFactory.createRule(new FileReader(fileName));
            rules.register(ruleApartadoA);
        }catch(Exception e) {
            throw new RuntimeException("OCURRIO UN ERROR " + e.getMessage());
        }



        //create a default rules engine and fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();

        rulesEngine.fire(rules, facts);

        return new TaxResult(result.value,999,"Impuesto de prueba c/ RULE");

    }



}
