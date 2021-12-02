package ar.edu.unq.ttip.alec.backend.service.dtos;

import java.util.List;


public class SearchRequest {

    private List<String> words;

    protected SearchRequest(){}

    public SearchRequest(List<String> words){
        this.words=words;
    }

    public List<String> getWords() {
        return words;
    }
}
