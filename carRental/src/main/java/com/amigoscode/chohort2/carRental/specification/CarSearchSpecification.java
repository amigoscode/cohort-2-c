package com.amigoscode.chohort2.carRental.specification;

import static com.amigoscode.chohort2.carRental.abstracts.AbstractAuditingEntity_.createdDate;

import static com.amigoscode.chohort2.carRental.car.Car_.*;

import com.amigoscode.chohort2.carRental.car.Car;
import com.amigoscode.chohort2.carRental.car.VM.CarSearchVM;
import com.amigoscode.chohort2.carRental.carProvider.CarProvider;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUser;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUser_;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarSearchSpecification {

    public static Specification<Car> carSearch(CarSearchVM carSearchVM) {
        return ((root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            Join<CarProviderUser, CarProvider> providerUserJoin = cq.from(CarProviderUser.class).join(CarProviderUser_.carProvider);

            if (carSearchVM.getProviderId() != null && carSearchVM.getProviderId() != 0) {
                predicates.add(cb.equal(providerUserJoin.get(CarProviderUser_.userId.getName()), carSearchVM.getProviderId()));
            }

            predicates.add(Util.equal(root, cb, brandCode, carSearchVM.getBrandCode()));
            predicates.add(Util.equal(root, cb, brandModelCode, carSearchVM.getBrandModelCode()));
            predicates.add(Util.equal(root, cb, productionYear, carSearchVM.getProductionYear()));
            predicates.add(Util.equal(root, cb, categoryCode, carSearchVM.getCategoryCode()));


            cq.orderBy(cb.desc(root.get(createdDate)));

            return cb.and(predicates
                    .stream()
                    .filter(Objects::nonNull)
                    .toArray(Predicate[]::new));


        });
    }

}
