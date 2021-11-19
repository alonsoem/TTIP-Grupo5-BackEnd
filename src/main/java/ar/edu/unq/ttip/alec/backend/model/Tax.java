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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Entity
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String url;

    @ManyToOne
    private Broker broker;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Rule> allRules= new ArrayList<>();

    public void addRule(Rule rule){
        allRules.add(rule);
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public Broker getBroker() {
        return broker;
    }

    public Tax(String name, String url){
        this.name=name;
        this.url=url;
    }
    public Tax(String name){
        this.name=name;
    }
    public Tax(){}

    public TaxResult calculateWith(Facts facts) {

        RuleResult result = new RuleResult();//Rule result set in zero
        facts.put("result", result);

        Rules rules = new Rules();
        allRules.stream().forEach(rule -> rules.register(rule.toMVEL()));

        RulesEngineParameters parameters = new RulesEngineParameters().skipOnFirstAppliedRule(true);
        RulesEngine rulesEngine = new DefaultRulesEngine(parameters);

        rulesEngine.fire(rules, facts);

        return new TaxResult(result.value,id,name,url);

    }

    public String getUrl() {
        return url;
    }

    public List<Rule> getRules(){
        return this.allRules.stream().sorted(Comparator.comparingInt(Rule::getPriority))
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void removeRule(Rule rule) {
        allRules.remove(rule);
    }

    public Integer getMaxPriority(){
        Integer priority=1;
        if (allRules.size()>0){
            priority = allRules.stream().max(Comparator.comparingInt(Rule::getPriority)).get().getPriority()+1;
        }
        return priority;
    }

    public List<Rule> swapRules(Integer ruleIdFrom, Integer ruleIdTo) {
        return allRules.stream()
                .filter(r->r.getId()==ruleIdFrom || r.getId()==ruleIdTo)
                .collect(Collectors.toList());
    }
}