package com.amigoscode.chohort2.carRental.specification;

import static com.amigoscode.chohort2.carRental.abstracts.AbstractAuditingEntity_.createdDate;

import static com.amigoscode.chohort2.carRental.car.Car_.*;

import com.amigoscode.chohort2.carRental.car.Car;
import com.amigoscode.chohort2.carRental.car.VM.CarSearchByProviderUserVM;
import com.amigoscode.chohort2.carRental.car.VM.CarSearchVM;
import com.amigoscode.chohort2.carRental.carProvider.CarProvider;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUser;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUser_;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarSearchSpecification {



    public static Specification<Car> carSearch(CarSearchByProviderUserVM carSearchVM) {
        return ((root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            Join<CarProviderUser, CarProvider> providerUserJoin = cq.from(CarProviderUser.class).join(CarProviderUser_.carProvider);

            if (carSearchVM.getProviderUserId() != null && carSearchVM.getProviderUserId() != 0) {
                predicates.add(cb.equal(providerUserJoin.getParent().get(CarProviderUser_.userId), carSearchVM.getProviderUserId()));
            }

            return carSearch(carSearchVM, root, cq, cb, predicates);

        });
    }


    public static Specification<Car> carSearch(CarSearchVM vm) {
        return ((root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            return carSearch(vm, root, cq, cb, predicates);


        });
    }

    private static Predicate carSearch(CarSearchVM vm, Root<Car> root, CriteriaQuery<?> cq, CriteriaBuilder cb, List<Predicate> predicates) {

        return getPredicate(vm, root, cq, cb, predicates);

    }

    private static Predicate getPredicate(CarSearchVM vm, Root<Car> root, CriteriaQuery<?> cq, CriteriaBuilder cb, List<Predicate> predicates) {
        predicates.add(Util.in(root, carProviderId, vm.getCarProviderIds()));
        predicates.add(Util.equal(root, cb, registrationNumber, vm.getRegistrationNumber()));
        predicates.add(Util.equal(root, cb, brandCode, vm.getBrandCode()));
        predicates.add(Util.equal(root, cb, brandModelCode, vm.getBrandModelCode()));
        predicates.add(Util.betweenDate(root, cb, productionYear, vm.getProductionYearFrom(),vm.getProductionYearTo()));
        predicates.add(Util.between(root, cb, maxSpeed, vm.getMaxSpeedFrom(),vm.getMaxSpeedTo()));
        predicates.add(Util.between(root, cb, horsePower, vm.getHorsePowerFrom(),vm.getHorsePowerTo()));
        predicates.add(Util.equal(root, cb, rgbCode, vm.getRgbCode()));
        predicates.add(Util.like(root, cb, description, vm.getDescription()));
        predicates.add(Util.between(root, cb, price, vm.getPriceFrom(),vm.getPriceTo()));
        predicates.add(Util.equal(root, cb, categoryCode, vm.getCategoryCode()));
        predicates.add(Util.equal(root, cb, bookingStatusCode, vm.getBookingStatusCode()));

        cq.orderBy(cb.desc(root.get(createdDate)));
        return cb.and(predicates
                .stream()
                .filter(Objects::nonNull)
                .toArray(Predicate[]::new));
    }
}
