package io.github.ahmedktarii.degitalesingage.Services;

import io.github.ahmedktarii.degitalesingage.Entities.Sign;
import io.github.ahmedktarii.degitalesingage.Repositories.SignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class signService {
    @Autowired
    private SignRepository signrepository;

    void save(Sign sign) {
        signrepository.save(sign);
    }
}
