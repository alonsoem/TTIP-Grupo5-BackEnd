package ar.edu.unq.desapp.grupoj.backenddesappapi.service;
import ar.edu.unq.desapp.grupoj.backenddesappapi.config.MessagingConfig;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.*;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.titles.Title;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.Critic;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.User;
import ar.edu.unq.desapp.grupoj.backenddesappapi.repository.*;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.*;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private  ReviewRepository reviewRepo;

    @Autowired
    private  LanguageService languageSrvc;

    @Autowired
    private TitleService titleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CriticService criticService;

    @Autowired
    private ReportRepository reportRepo;


    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SourceRepository sourceRepo;

    @Autowired
    private LocationRepository locationRepo;

    @Autowired
    private ReviewCriteriaRepository reviewCriteriaRepository;

    @Autowired
    private RabbitTemplate template;


    public ReviewService(){ }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
/*
        save(new ReviewDTO(1, "Maso, para un domingo zafa","pochoclera",3,true,language));
        User user = new User (source,"fernando.test@gmail.com","Fernando",location);
        user.addReview(new Review(1, "Maso, para un domingo zafa","pochoclera",3,true,language));
        Language language2 = new Language("English");

        user.addReview(new Review(3, "Muy mala pelicula","No la entendi",1,true,language));
        user.addReview(new Review(5, "Pectacular, alta peli pero muy larga!","Increibles efecto especiales",3,false,language));

        //userRepository.save(user);


        Critic critic = new Critic(3,"criticoEspecialista@yahoo.com");
        critic.addReview(new ReviewPremium(4, "La mas pior!","Terriblemente aburrida.",5,true, language2));
        criticRepository.save(critic);
*/
    }

    public List<Review> findAll() {
        return reviewRepo.findAll();
    }

    public List<Review> findAllByIdTitle(Integer idTitle) throws NonExistentTitleException {
        Title title = titleService.getByTitleId(idTitle);
        return reviewRepo.findAllByTitleId(title.getTitleId());
    }

    @Transactional
    public Review save(ReviewDTO aReview) throws NonExistentLocationException, NonExistentLanguageException, NonExistentSourceException, NonExistentTitleException, UserAlreadyReviewTitle {
        Language language= checkLanguage(aReview.getLanguageId());
        User user = userService.getBySourceAndUserIdAndNickId(aReview.getUser().getSourceId(),
                                                            aReview.getUser().getUserId(),
                                                            aReview.getUser().getUserNick(),
                                                            aReview.getUser().getLocationId());

        Review review = aReview.toModel(language,user);
        this.checkUniqueReviewer(review,user);
        reviewRepo.save(review);

        titleService.addReviewToTitle(review,aReview.getTitleId());


        return review;
    }

    private void checkUniqueReviewer(Review review, Critic user) throws UserAlreadyReviewTitle {
        if (reviewRepo.findReviewsByTitleIdAndUser(review.getTitleId(),user).size()>0) {
            throw new UserAlreadyReviewTitle(review.getTitleId(), user.getUniqueIdString());
        }
    }


    @Transactional
    public Review savePremium(ReviewPremiumDTO aReview) throws NonExistentSourceException, NonExistentTitleException, NonExistentLanguageException, UserAlreadyReviewTitle, NonExistentLocationException, NonExistentCriticException {
        Language language= checkLanguage(aReview.languageId);

        Critic critic = criticService.getBySourceAndCriticId(aReview.critic.getSourceId(),aReview.critic.getUserId(),aReview.critic.getLocationId());


        ReviewPremium review = aReview.toModel(language,critic);
        this.checkUniqueReviewer(review,critic);
        reviewRepo.save(review);

        titleService.addReviewToTitle(review,aReview.titleId);

        return review;

    }

    @Transactional
    public Rates rate(RateDTO rateDto) throws NonExistentReviewException, NonExistentSourceException, NonExistentLocationException {
        Review aReview= reviewRepo.findById(rateDto.reviewId).orElseThrow(() -> new NonExistentReviewException(rateDto.reviewId));
        this.rateReview(aReview,rateDto.user,rateDto.rateType);
        reviewRepo.save(aReview);

        return aReview.getReviewRate();
    }

    private void rateReview(Review review, UserDTO user, RateType rateType) throws NonExistentLocationException, NonExistentSourceException {
        User rateUser = userService.getBySourceAndUserIdAndNickId(user.getSourceId(),
                                                                    user.getUserId(),
                                                                    user.getUserNick(),
                                                                    user.getLocationId());
        review.addRate(new ReviewRate(rateType,rateUser,review));
    }


    @Transactional
    public ReviewReport report(ReportDTO reportDto) throws NonExistentReviewException, NonExistentLocationException, NonExistentSourceException {
        Review aReview= reviewRepo.findById(reportDto.reviewId).orElseThrow(() -> new NonExistentReviewException(reportDto.reviewId));
        User user= userService.getBySourceAndUserIdAndNickId(reportDto.user.getSourceId(),
                                                            reportDto.user.getUserId(),
                                                            reportDto.user.getUserNick(),
                                                            reportDto.user.getLocationId());
        ReviewReport report= new ReviewReport(reportDto.reason,user);
        reportRepo.save(report);

        aReview.addReport(report);
        reviewRepo.save(aReview);
        return report;
    }


    private Language checkLanguage(Integer languageId) throws NonExistentLanguageException {
        return languageSrvc.getById(languageId);
    }

    public Page<Review> getReviews(ReviewPage reviewPage, ReviewSearchCriteria reviewSearchCriteria){
        return reviewCriteriaRepository.findAllWithFilters(reviewPage, reviewSearchCriteria);

    }

    public List<ReviewReport> findAllReports() {
        return reportRepo.findAll();
    }



}
