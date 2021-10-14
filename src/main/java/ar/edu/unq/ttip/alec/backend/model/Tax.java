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
package ar.edu.unq.ttip.alec.backend.model;

import ar.edu.unq.ttip.alec.backend.model.enumClasses.Apartado;
import ar.edu.unq.ttip.alec.backend.model.enumClasses.Province;
import ar.edu.unq.ttip.alec.backend.model.rules.Fact;
import ar.edu.unq.ttip.alec.backend.model.rules.Rule;
import ar.edu.unq.ttip.alec.backend.model.rules.RuleResult;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.api.RulesEngineParameters;
import org.jeasy.rules.core.DefaultRulesEngine;


import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String url;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Rule> allRules= new ArrayList<>();

    public void addRule(Rule rule){
        allRules.add(rule);
    }

    public Tax(String name,String url){
        this.name=name;
        this.url=url;
    }
    public Tax(String name){
        this.name=name;
    }
    public Tax(){}

    public TaxResult calculateWith(Facts facts) {

        RuleResult result = new RuleResult();
        facts.put("result", result);

        try {
            facts.put("apartadoClass",  Class.forName("ar.edu.unq.ttip.alec.backend.model.enumClasses.Apartado"));
            facts.put("provinceClass",  Class.forName("ar.edu.unq.ttip.alec.backend.model.enumClasses.Province"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Rules rules = new Rules();
        allRules.stream().forEach(rule -> rules.register(rule.toMVEL()));

        RulesEngineParameters parameters = new RulesEngineParameters().skipOnFirstAppliedRule(true);
        RulesEngine rulesEngine = new DefaultRulesEngine(parameters);

        rulesEngine.fire(rules, facts);

        return new TaxResult(result.value,id,name,url);

    }

    public List<Fact> getFacts(){
        return this.allRules.stream()
                .map(each->each.getFacts())
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
