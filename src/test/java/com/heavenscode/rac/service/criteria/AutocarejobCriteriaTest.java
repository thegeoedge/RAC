package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AutocarejobCriteriaTest {

    @Test
    void newAutocarejobCriteriaHasAllFiltersNullTest() {
        var autocarejobCriteria = new AutocarejobCriteria();
        assertThat(autocarejobCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void autocarejobCriteriaFluentMethodsCreatesFiltersTest() {
        var autocarejobCriteria = new AutocarejobCriteria();

        setAllFilters(autocarejobCriteria);

        assertThat(autocarejobCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void autocarejobCriteriaCopyCreatesNullFilterTest() {
        var autocarejobCriteria = new AutocarejobCriteria();
        var copy = autocarejobCriteria.copy();

        assertThat(autocarejobCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(autocarejobCriteria)
        );
    }

    @Test
    void autocarejobCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var autocarejobCriteria = new AutocarejobCriteria();
        setAllFilters(autocarejobCriteria);

        var copy = autocarejobCriteria.copy();

        assertThat(autocarejobCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(autocarejobCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var autocarejobCriteria = new AutocarejobCriteria();

        assertThat(autocarejobCriteria).hasToString("AutocarejobCriteria{}");
    }

    private static void setAllFilters(AutocarejobCriteria autocarejobCriteria) {
        autocarejobCriteria.id();
        autocarejobCriteria.jobnumber();
        autocarejobCriteria.vehicleid();
        autocarejobCriteria.vehiclenumber();
        autocarejobCriteria.millage();
        autocarejobCriteria.nextmillage();
        autocarejobCriteria.nextservicedate();
        autocarejobCriteria.vehicletypeid();
        autocarejobCriteria.jobtypeid();
        autocarejobCriteria.jobtypename();
        autocarejobCriteria.jobopenby();
        autocarejobCriteria.jobopentime();
        autocarejobCriteria.lmu();
        autocarejobCriteria.lmd();
        autocarejobCriteria.specialrquirments();
        autocarejobCriteria.specialinstructions();
        autocarejobCriteria.remarks();
        autocarejobCriteria.nextserviceinstructions();
        autocarejobCriteria.lastserviceinstructions();
        autocarejobCriteria.isadvisorchecked();
        autocarejobCriteria.isempallocated();
        autocarejobCriteria.jobclosetime();
        autocarejobCriteria.isjobclose();
        autocarejobCriteria.isfeedback();
        autocarejobCriteria.feedbackstatusid();
        autocarejobCriteria.customername();
        autocarejobCriteria.customertel();
        autocarejobCriteria.customerid();
        autocarejobCriteria.advisorfinalcheck();
        autocarejobCriteria.jobdate();
        autocarejobCriteria.iscompanyservice();
        autocarejobCriteria.freeservicenumber();
        autocarejobCriteria.companyid();
        autocarejobCriteria.updatetocustomer();
        autocarejobCriteria.nextgearoilmilage();
        autocarejobCriteria.isjobinvoiced();
        autocarejobCriteria.iswaiting();
        autocarejobCriteria.iscustomercomment();
        autocarejobCriteria.imagefolder();
        autocarejobCriteria.frontimage();
        autocarejobCriteria.leftimage();
        autocarejobCriteria.rightimage();
        autocarejobCriteria.backimage();
        autocarejobCriteria.dashboardimage();
        autocarejobCriteria.distinct();
    }

    private static Condition<AutocarejobCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getJobnumber()) &&
                condition.apply(criteria.getVehicleid()) &&
                condition.apply(criteria.getVehiclenumber()) &&
                condition.apply(criteria.getMillage()) &&
                condition.apply(criteria.getNextmillage()) &&
                condition.apply(criteria.getNextservicedate()) &&
                condition.apply(criteria.getVehicletypeid()) &&
                condition.apply(criteria.getJobtypeid()) &&
                condition.apply(criteria.getJobtypename()) &&
                condition.apply(criteria.getJobopenby()) &&
                condition.apply(criteria.getJobopentime()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getSpecialrquirments()) &&
                condition.apply(criteria.getSpecialinstructions()) &&
                condition.apply(criteria.getRemarks()) &&
                condition.apply(criteria.getNextserviceinstructions()) &&
                condition.apply(criteria.getLastserviceinstructions()) &&
                condition.apply(criteria.getIsadvisorchecked()) &&
                condition.apply(criteria.getIsempallocated()) &&
                condition.apply(criteria.getJobclosetime()) &&
                condition.apply(criteria.getIsjobclose()) &&
                condition.apply(criteria.getIsfeedback()) &&
                condition.apply(criteria.getFeedbackstatusid()) &&
                condition.apply(criteria.getCustomername()) &&
                condition.apply(criteria.getCustomertel()) &&
                condition.apply(criteria.getCustomerid()) &&
                condition.apply(criteria.getAdvisorfinalcheck()) &&
                condition.apply(criteria.getJobdate()) &&
                condition.apply(criteria.getIscompanyservice()) &&
                condition.apply(criteria.getFreeservicenumber()) &&
                condition.apply(criteria.getCompanyid()) &&
                condition.apply(criteria.getUpdatetocustomer()) &&
                condition.apply(criteria.getNextgearoilmilage()) &&
                condition.apply(criteria.getIsjobinvoiced()) &&
                condition.apply(criteria.getIswaiting()) &&
                condition.apply(criteria.getIscustomercomment()) &&
                condition.apply(criteria.getImagefolder()) &&
                condition.apply(criteria.getFrontimage()) &&
                condition.apply(criteria.getLeftimage()) &&
                condition.apply(criteria.getRightimage()) &&
                condition.apply(criteria.getBackimage()) &&
                condition.apply(criteria.getDashboardimage()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AutocarejobCriteria> copyFiltersAre(AutocarejobCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getJobnumber(), copy.getJobnumber()) &&
                condition.apply(criteria.getVehicleid(), copy.getVehicleid()) &&
                condition.apply(criteria.getVehiclenumber(), copy.getVehiclenumber()) &&
                condition.apply(criteria.getMillage(), copy.getMillage()) &&
                condition.apply(criteria.getNextmillage(), copy.getNextmillage()) &&
                condition.apply(criteria.getNextservicedate(), copy.getNextservicedate()) &&
                condition.apply(criteria.getVehicletypeid(), copy.getVehicletypeid()) &&
                condition.apply(criteria.getJobtypeid(), copy.getJobtypeid()) &&
                condition.apply(criteria.getJobtypename(), copy.getJobtypename()) &&
                condition.apply(criteria.getJobopenby(), copy.getJobopenby()) &&
                condition.apply(criteria.getJobopentime(), copy.getJobopentime()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getSpecialrquirments(), copy.getSpecialrquirments()) &&
                condition.apply(criteria.getSpecialinstructions(), copy.getSpecialinstructions()) &&
                condition.apply(criteria.getRemarks(), copy.getRemarks()) &&
                condition.apply(criteria.getNextserviceinstructions(), copy.getNextserviceinstructions()) &&
                condition.apply(criteria.getLastserviceinstructions(), copy.getLastserviceinstructions()) &&
                condition.apply(criteria.getIsadvisorchecked(), copy.getIsadvisorchecked()) &&
                condition.apply(criteria.getIsempallocated(), copy.getIsempallocated()) &&
                condition.apply(criteria.getJobclosetime(), copy.getJobclosetime()) &&
                condition.apply(criteria.getIsjobclose(), copy.getIsjobclose()) &&
                condition.apply(criteria.getIsfeedback(), copy.getIsfeedback()) &&
                condition.apply(criteria.getFeedbackstatusid(), copy.getFeedbackstatusid()) &&
                condition.apply(criteria.getCustomername(), copy.getCustomername()) &&
                condition.apply(criteria.getCustomertel(), copy.getCustomertel()) &&
                condition.apply(criteria.getCustomerid(), copy.getCustomerid()) &&
                condition.apply(criteria.getAdvisorfinalcheck(), copy.getAdvisorfinalcheck()) &&
                condition.apply(criteria.getJobdate(), copy.getJobdate()) &&
                condition.apply(criteria.getIscompanyservice(), copy.getIscompanyservice()) &&
                condition.apply(criteria.getFreeservicenumber(), copy.getFreeservicenumber()) &&
                condition.apply(criteria.getCompanyid(), copy.getCompanyid()) &&
                condition.apply(criteria.getUpdatetocustomer(), copy.getUpdatetocustomer()) &&
                condition.apply(criteria.getNextgearoilmilage(), copy.getNextgearoilmilage()) &&
                condition.apply(criteria.getIsjobinvoiced(), copy.getIsjobinvoiced()) &&
                condition.apply(criteria.getIswaiting(), copy.getIswaiting()) &&
                condition.apply(criteria.getIscustomercomment(), copy.getIscustomercomment()) &&
                condition.apply(criteria.getImagefolder(), copy.getImagefolder()) &&
                condition.apply(criteria.getFrontimage(), copy.getFrontimage()) &&
                condition.apply(criteria.getLeftimage(), copy.getLeftimage()) &&
                condition.apply(criteria.getRightimage(), copy.getRightimage()) &&
                condition.apply(criteria.getBackimage(), copy.getBackimage()) &&
                condition.apply(criteria.getDashboardimage(), copy.getDashboardimage()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
