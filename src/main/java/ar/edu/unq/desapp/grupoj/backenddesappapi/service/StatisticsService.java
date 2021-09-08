package ar.edu.unq.desapp.grupoj.backenddesappapi.service;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.FrontUser;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Statistics;
import ar.edu.unq.desapp.grupoj.backenddesappapi.repository.EstatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StatisticsService {

    @Autowired
    private EstatisticsRepository repo;

    @Transactional
    public List<Statistics> getAllStatisticsByPlatform() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        FrontUser userDetails = (FrontUser) auth.getPrincipal();

        return repo.findAllByPlatformId(userDetails.getPlatformId());
    }

    @Transactional
    public void update(String methodName,Integer platformId){
        Statistics methodData = repo
                .findByMethodNameAndPlatformId(methodName,platformId)
                .orElse(new Statistics(methodName,platformId));

        methodData.update();
        repo.save(methodData);
    }


}
