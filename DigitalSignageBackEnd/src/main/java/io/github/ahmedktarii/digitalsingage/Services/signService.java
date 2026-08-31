package io.github.ahmedktarii.digitalsingage.Services;

import io.github.ahmedktarii.digitalsingage.Entities.Sign;
import io.github.ahmedktarii.digitalsingage.Repositories.SignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class signService {
    @Autowired
    private SignRepository signrepository;

    public void save(Sign sign) {
        signrepository.save(sign);
    }
    
}
