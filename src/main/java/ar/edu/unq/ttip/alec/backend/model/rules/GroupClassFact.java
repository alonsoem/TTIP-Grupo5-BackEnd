package ar.edu.unq.ttip.alec.backend.model.rules;

import javax.persistence.Entity;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class GroupClassFact extends GroupFact{

    private String className;

    public GroupClassFact(String name, String description,String className){
        super(name,description);
        this.className=className;
    }
    protected GroupClassFact(){}


    @Override
    public List<Fact> getFacts() throws ClassNotFoundException {
        return Arrays.stream(Class.forName(this.className).getEnumConstants())
                .map((each)->{
                                Fact fact = new Fact(each.toString(),each.toString());
                                fact.fixed=false;
                                fact.setValue(each);
                                return fact;
                }
        ).collect(Collectors.toList());
    }

}

