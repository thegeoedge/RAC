package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Autocarejob} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.AutocarejobResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /autocarejobs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AutocarejobCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter jobnumber;

    private IntegerFilter vehicleid;

    private StringFilter vehiclenumber;

    private FloatFilter millage;

    private FloatFilter nextmillage;

    private LocalDateFilter nextservicedate;

    private IntegerFilter vehicletypeid;

    private IntegerFilter jobtypeid;

    private StringFilter jobtypename;

    private IntegerFilter jobopenby;

    private InstantFilter jobopentime;

    private IntegerFilter lmu;

    private InstantFilter lmd;

    private StringFilter specialrquirments;

    private StringFilter specialinstructions;

    private StringFilter remarks;

    private StringFilter nextserviceinstructions;

    private StringFilter lastserviceinstructions;

    private BooleanFilter isadvisorchecked;

    private BooleanFilter isempallocated;

    private InstantFilter jobclosetime;

    private BooleanFilter isjobclose;

    private BooleanFilter isfeedback;

    private IntegerFilter feedbackstatusid;

    private StringFilter customername;

    private StringFilter customertel;

    private IntegerFilter customerid;

    private BooleanFilter advisorfinalcheck;

    private InstantFilter jobdate;

    private BooleanFilter iscompanyservice;

    private StringFilter freeservicenumber;

    private IntegerFilter companyid;

    private BooleanFilter updatetocustomer;

    private StringFilter nextgearoilmilage;

    private BooleanFilter isjobinvoiced;

    private BooleanFilter iswaiting;

    private BooleanFilter iscustomercomment;

    private StringFilter imagefolder;

    private StringFilter frontimage;

    private StringFilter leftimage;

    private StringFilter rightimage;

    private StringFilter backimage;

    private StringFilter dashboardimage;

    private Boolean distinct;

    public AutocarejobCriteria() {}

    public AutocarejobCriteria(AutocarejobCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.jobnumber = other.optionalJobnumber().map(IntegerFilter::copy).orElse(null);
        this.vehicleid = other.optionalVehicleid().map(IntegerFilter::copy).orElse(null);
        this.vehiclenumber = other.optionalVehiclenumber().map(StringFilter::copy).orElse(null);
        this.millage = other.optionalMillage().map(FloatFilter::copy).orElse(null);
        this.nextmillage = other.optionalNextmillage().map(FloatFilter::copy).orElse(null);
        this.nextservicedate = other.optionalNextservicedate().map(LocalDateFilter::copy).orElse(null);
        this.vehicletypeid = other.optionalVehicletypeid().map(IntegerFilter::copy).orElse(null);
        this.jobtypeid = other.optionalJobtypeid().map(IntegerFilter::copy).orElse(null);
        this.jobtypename = other.optionalJobtypename().map(StringFilter::copy).orElse(null);
        this.jobopenby = other.optionalJobopenby().map(IntegerFilter::copy).orElse(null);
        this.jobopentime = other.optionalJobopentime().map(InstantFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.specialrquirments = other.optionalSpecialrquirments().map(StringFilter::copy).orElse(null);
        this.specialinstructions = other.optionalSpecialinstructions().map(StringFilter::copy).orElse(null);
        this.remarks = other.optionalRemarks().map(StringFilter::copy).orElse(null);
        this.nextserviceinstructions = other.optionalNextserviceinstructions().map(StringFilter::copy).orElse(null);
        this.lastserviceinstructions = other.optionalLastserviceinstructions().map(StringFilter::copy).orElse(null);
        this.isadvisorchecked = other.optionalIsadvisorchecked().map(BooleanFilter::copy).orElse(null);
        this.isempallocated = other.optionalIsempallocated().map(BooleanFilter::copy).orElse(null);
        this.jobclosetime = other.optionalJobclosetime().map(InstantFilter::copy).orElse(null);
        this.isjobclose = other.optionalIsjobclose().map(BooleanFilter::copy).orElse(null);
        this.isfeedback = other.optionalIsfeedback().map(BooleanFilter::copy).orElse(null);
        this.feedbackstatusid = other.optionalFeedbackstatusid().map(IntegerFilter::copy).orElse(null);
        this.customername = other.optionalCustomername().map(StringFilter::copy).orElse(null);
        this.customertel = other.optionalCustomertel().map(StringFilter::copy).orElse(null);
        this.customerid = other.optionalCustomerid().map(IntegerFilter::copy).orElse(null);
        this.advisorfinalcheck = other.optionalAdvisorfinalcheck().map(BooleanFilter::copy).orElse(null);
        this.jobdate = other.optionalJobdate().map(InstantFilter::copy).orElse(null);
        this.iscompanyservice = other.optionalIscompanyservice().map(BooleanFilter::copy).orElse(null);
        this.freeservicenumber = other.optionalFreeservicenumber().map(StringFilter::copy).orElse(null);
        this.companyid = other.optionalCompanyid().map(IntegerFilter::copy).orElse(null);
        this.updatetocustomer = other.optionalUpdatetocustomer().map(BooleanFilter::copy).orElse(null);
        this.nextgearoilmilage = other.optionalNextgearoilmilage().map(StringFilter::copy).orElse(null);
        this.isjobinvoiced = other.optionalIsjobinvoiced().map(BooleanFilter::copy).orElse(null);
        this.iswaiting = other.optionalIswaiting().map(BooleanFilter::copy).orElse(null);
        this.iscustomercomment = other.optionalIscustomercomment().map(BooleanFilter::copy).orElse(null);
        this.imagefolder = other.optionalImagefolder().map(StringFilter::copy).orElse(null);
        this.frontimage = other.optionalFrontimage().map(StringFilter::copy).orElse(null);
        this.leftimage = other.optionalLeftimage().map(StringFilter::copy).orElse(null);
        this.rightimage = other.optionalRightimage().map(StringFilter::copy).orElse(null);
        this.backimage = other.optionalBackimage().map(StringFilter::copy).orElse(null);
        this.dashboardimage = other.optionalDashboardimage().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AutocarejobCriteria copy() {
        return new AutocarejobCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getJobnumber() {
        return jobnumber;
    }

    public Optional<IntegerFilter> optionalJobnumber() {
        return Optional.ofNullable(jobnumber);
    }

    public IntegerFilter jobnumber() {
        if (jobnumber == null) {
            setJobnumber(new IntegerFilter());
        }
        return jobnumber;
    }

    public void setJobnumber(IntegerFilter jobnumber) {
        this.jobnumber = jobnumber;
    }

    public IntegerFilter getVehicleid() {
        return vehicleid;
    }

    public Optional<IntegerFilter> optionalVehicleid() {
        return Optional.ofNullable(vehicleid);
    }

    public IntegerFilter vehicleid() {
        if (vehicleid == null) {
            setVehicleid(new IntegerFilter());
        }
        return vehicleid;
    }

    public void setVehicleid(IntegerFilter vehicleid) {
        this.vehicleid = vehicleid;
    }

    public StringFilter getVehiclenumber() {
        return vehiclenumber;
    }

    public Optional<StringFilter> optionalVehiclenumber() {
        return Optional.ofNullable(vehiclenumber);
    }

    public StringFilter vehiclenumber() {
        if (vehiclenumber == null) {
            setVehiclenumber(new StringFilter());
        }
        return vehiclenumber;
    }

    public void setVehiclenumber(StringFilter vehiclenumber) {
        this.vehiclenumber = vehiclenumber;
    }

    public FloatFilter getMillage() {
        return millage;
    }

    public Optional<FloatFilter> optionalMillage() {
        return Optional.ofNullable(millage);
    }

    public FloatFilter millage() {
        if (millage == null) {
            setMillage(new FloatFilter());
        }
        return millage;
    }

    public void setMillage(FloatFilter millage) {
        this.millage = millage;
    }

    public FloatFilter getNextmillage() {
        return nextmillage;
    }

    public Optional<FloatFilter> optionalNextmillage() {
        return Optional.ofNullable(nextmillage);
    }

    public FloatFilter nextmillage() {
        if (nextmillage == null) {
            setNextmillage(new FloatFilter());
        }
        return nextmillage;
    }

    public void setNextmillage(FloatFilter nextmillage) {
        this.nextmillage = nextmillage;
    }

    public LocalDateFilter getNextservicedate() {
        return nextservicedate;
    }

    public Optional<LocalDateFilter> optionalNextservicedate() {
        return Optional.ofNullable(nextservicedate);
    }

    public LocalDateFilter nextservicedate() {
        if (nextservicedate == null) {
            setNextservicedate(new LocalDateFilter());
        }
        return nextservicedate;
    }

    public void setNextservicedate(LocalDateFilter nextservicedate) {
        this.nextservicedate = nextservicedate;
    }

    public IntegerFilter getVehicletypeid() {
        return vehicletypeid;
    }

    public Optional<IntegerFilter> optionalVehicletypeid() {
        return Optional.ofNullable(vehicletypeid);
    }

    public IntegerFilter vehicletypeid() {
        if (vehicletypeid == null) {
            setVehicletypeid(new IntegerFilter());
        }
        return vehicletypeid;
    }

    public void setVehicletypeid(IntegerFilter vehicletypeid) {
        this.vehicletypeid = vehicletypeid;
    }

    public IntegerFilter getJobtypeid() {
        return jobtypeid;
    }

    public Optional<IntegerFilter> optionalJobtypeid() {
        return Optional.ofNullable(jobtypeid);
    }

    public IntegerFilter jobtypeid() {
        if (jobtypeid == null) {
            setJobtypeid(new IntegerFilter());
        }
        return jobtypeid;
    }

    public void setJobtypeid(IntegerFilter jobtypeid) {
        this.jobtypeid = jobtypeid;
    }

    public StringFilter getJobtypename() {
        return jobtypename;
    }

    public Optional<StringFilter> optionalJobtypename() {
        return Optional.ofNullable(jobtypename);
    }

    public StringFilter jobtypename() {
        if (jobtypename == null) {
            setJobtypename(new StringFilter());
        }
        return jobtypename;
    }

    public void setJobtypename(StringFilter jobtypename) {
        this.jobtypename = jobtypename;
    }

    public IntegerFilter getJobopenby() {
        return jobopenby;
    }

    public Optional<IntegerFilter> optionalJobopenby() {
        return Optional.ofNullable(jobopenby);
    }

    public IntegerFilter jobopenby() {
        if (jobopenby == null) {
            setJobopenby(new IntegerFilter());
        }
        return jobopenby;
    }

    public void setJobopenby(IntegerFilter jobopenby) {
        this.jobopenby = jobopenby;
    }

    public InstantFilter getJobopentime() {
        return jobopentime;
    }

    public Optional<InstantFilter> optionalJobopentime() {
        return Optional.ofNullable(jobopentime);
    }

    public InstantFilter jobopentime() {
        if (jobopentime == null) {
            setJobopentime(new InstantFilter());
        }
        return jobopentime;
    }

    public void setJobopentime(InstantFilter jobopentime) {
        this.jobopentime = jobopentime;
    }

    public IntegerFilter getLmu() {
        return lmu;
    }

    public Optional<IntegerFilter> optionalLmu() {
        return Optional.ofNullable(lmu);
    }

    public IntegerFilter lmu() {
        if (lmu == null) {
            setLmu(new IntegerFilter());
        }
        return lmu;
    }

    public void setLmu(IntegerFilter lmu) {
        this.lmu = lmu;
    }

    public InstantFilter getLmd() {
        return lmd;
    }

    public Optional<InstantFilter> optionalLmd() {
        return Optional.ofNullable(lmd);
    }

    public InstantFilter lmd() {
        if (lmd == null) {
            setLmd(new InstantFilter());
        }
        return lmd;
    }

    public void setLmd(InstantFilter lmd) {
        this.lmd = lmd;
    }

    public StringFilter getSpecialrquirments() {
        return specialrquirments;
    }

    public Optional<StringFilter> optionalSpecialrquirments() {
        return Optional.ofNullable(specialrquirments);
    }

    public StringFilter specialrquirments() {
        if (specialrquirments == null) {
            setSpecialrquirments(new StringFilter());
        }
        return specialrquirments;
    }

    public void setSpecialrquirments(StringFilter specialrquirments) {
        this.specialrquirments = specialrquirments;
    }

    public StringFilter getSpecialinstructions() {
        return specialinstructions;
    }

    public Optional<StringFilter> optionalSpecialinstructions() {
        return Optional.ofNullable(specialinstructions);
    }

    public StringFilter specialinstructions() {
        if (specialinstructions == null) {
            setSpecialinstructions(new StringFilter());
        }
        return specialinstructions;
    }

    public void setSpecialinstructions(StringFilter specialinstructions) {
        this.specialinstructions = specialinstructions;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public Optional<StringFilter> optionalRemarks() {
        return Optional.ofNullable(remarks);
    }

    public StringFilter remarks() {
        if (remarks == null) {
            setRemarks(new StringFilter());
        }
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public StringFilter getNextserviceinstructions() {
        return nextserviceinstructions;
    }

    public Optional<StringFilter> optionalNextserviceinstructions() {
        return Optional.ofNullable(nextserviceinstructions);
    }

    public StringFilter nextserviceinstructions() {
        if (nextserviceinstructions == null) {
            setNextserviceinstructions(new StringFilter());
        }
        return nextserviceinstructions;
    }

    public void setNextserviceinstructions(StringFilter nextserviceinstructions) {
        this.nextserviceinstructions = nextserviceinstructions;
    }

    public StringFilter getLastserviceinstructions() {
        return lastserviceinstructions;
    }

    public Optional<StringFilter> optionalLastserviceinstructions() {
        return Optional.ofNullable(lastserviceinstructions);
    }

    public StringFilter lastserviceinstructions() {
        if (lastserviceinstructions == null) {
            setLastserviceinstructions(new StringFilter());
        }
        return lastserviceinstructions;
    }

    public void setLastserviceinstructions(StringFilter lastserviceinstructions) {
        this.lastserviceinstructions = lastserviceinstructions;
    }

    public BooleanFilter getIsadvisorchecked() {
        return isadvisorchecked;
    }

    public Optional<BooleanFilter> optionalIsadvisorchecked() {
        return Optional.ofNullable(isadvisorchecked);
    }

    public BooleanFilter isadvisorchecked() {
        if (isadvisorchecked == null) {
            setIsadvisorchecked(new BooleanFilter());
        }
        return isadvisorchecked;
    }

    public void setIsadvisorchecked(BooleanFilter isadvisorchecked) {
        this.isadvisorchecked = isadvisorchecked;
    }

    public BooleanFilter getIsempallocated() {
        return isempallocated;
    }

    public Optional<BooleanFilter> optionalIsempallocated() {
        return Optional.ofNullable(isempallocated);
    }

    public BooleanFilter isempallocated() {
        if (isempallocated == null) {
            setIsempallocated(new BooleanFilter());
        }
        return isempallocated;
    }

    public void setIsempallocated(BooleanFilter isempallocated) {
        this.isempallocated = isempallocated;
    }

    public InstantFilter getJobclosetime() {
        return jobclosetime;
    }

    public Optional<InstantFilter> optionalJobclosetime() {
        return Optional.ofNullable(jobclosetime);
    }

    public InstantFilter jobclosetime() {
        if (jobclosetime == null) {
            setJobclosetime(new InstantFilter());
        }
        return jobclosetime;
    }

    public void setJobclosetime(InstantFilter jobclosetime) {
        this.jobclosetime = jobclosetime;
    }

    public BooleanFilter getIsjobclose() {
        return isjobclose;
    }

    public Optional<BooleanFilter> optionalIsjobclose() {
        return Optional.ofNullable(isjobclose);
    }

    public BooleanFilter isjobclose() {
        if (isjobclose == null) {
            setIsjobclose(new BooleanFilter());
        }
        return isjobclose;
    }

    public void setIsjobclose(BooleanFilter isjobclose) {
        this.isjobclose = isjobclose;
    }

    public BooleanFilter getIsfeedback() {
        return isfeedback;
    }

    public Optional<BooleanFilter> optionalIsfeedback() {
        return Optional.ofNullable(isfeedback);
    }

    public BooleanFilter isfeedback() {
        if (isfeedback == null) {
            setIsfeedback(new BooleanFilter());
        }
        return isfeedback;
    }

    public void setIsfeedback(BooleanFilter isfeedback) {
        this.isfeedback = isfeedback;
    }

    public IntegerFilter getFeedbackstatusid() {
        return feedbackstatusid;
    }

    public Optional<IntegerFilter> optionalFeedbackstatusid() {
        return Optional.ofNullable(feedbackstatusid);
    }

    public IntegerFilter feedbackstatusid() {
        if (feedbackstatusid == null) {
            setFeedbackstatusid(new IntegerFilter());
        }
        return feedbackstatusid;
    }

    public void setFeedbackstatusid(IntegerFilter feedbackstatusid) {
        this.feedbackstatusid = feedbackstatusid;
    }

    public StringFilter getCustomername() {
        return customername;
    }

    public Optional<StringFilter> optionalCustomername() {
        return Optional.ofNullable(customername);
    }

    public StringFilter customername() {
        if (customername == null) {
            setCustomername(new StringFilter());
        }
        return customername;
    }

    public void setCustomername(StringFilter customername) {
        this.customername = customername;
    }

    public StringFilter getCustomertel() {
        return customertel;
    }

    public Optional<StringFilter> optionalCustomertel() {
        return Optional.ofNullable(customertel);
    }

    public StringFilter customertel() {
        if (customertel == null) {
            setCustomertel(new StringFilter());
        }
        return customertel;
    }

    public void setCustomertel(StringFilter customertel) {
        this.customertel = customertel;
    }

    public IntegerFilter getCustomerid() {
        return customerid;
    }

    public Optional<IntegerFilter> optionalCustomerid() {
        return Optional.ofNullable(customerid);
    }

    public IntegerFilter customerid() {
        if (customerid == null) {
            setCustomerid(new IntegerFilter());
        }
        return customerid;
    }

    public void setCustomerid(IntegerFilter customerid) {
        this.customerid = customerid;
    }

    public BooleanFilter getAdvisorfinalcheck() {
        return advisorfinalcheck;
    }

    public Optional<BooleanFilter> optionalAdvisorfinalcheck() {
        return Optional.ofNullable(advisorfinalcheck);
    }

    public BooleanFilter advisorfinalcheck() {
        if (advisorfinalcheck == null) {
            setAdvisorfinalcheck(new BooleanFilter());
        }
        return advisorfinalcheck;
    }

    public void setAdvisorfinalcheck(BooleanFilter advisorfinalcheck) {
        this.advisorfinalcheck = advisorfinalcheck;
    }

    public InstantFilter getJobdate() {
        return jobdate;
    }

    public Optional<InstantFilter> optionalJobdate() {
        return Optional.ofNullable(jobdate);
    }

    public InstantFilter jobdate() {
        if (jobdate == null) {
            setJobdate(new InstantFilter());
        }
        return jobdate;
    }

    public void setJobdate(InstantFilter jobdate) {
        this.jobdate = jobdate;
    }

    public BooleanFilter getIscompanyservice() {
        return iscompanyservice;
    }

    public Optional<BooleanFilter> optionalIscompanyservice() {
        return Optional.ofNullable(iscompanyservice);
    }

    public BooleanFilter iscompanyservice() {
        if (iscompanyservice == null) {
            setIscompanyservice(new BooleanFilter());
        }
        return iscompanyservice;
    }

    public void setIscompanyservice(BooleanFilter iscompanyservice) {
        this.iscompanyservice = iscompanyservice;
    }

    public StringFilter getFreeservicenumber() {
        return freeservicenumber;
    }

    public Optional<StringFilter> optionalFreeservicenumber() {
        return Optional.ofNullable(freeservicenumber);
    }

    public StringFilter freeservicenumber() {
        if (freeservicenumber == null) {
            setFreeservicenumber(new StringFilter());
        }
        return freeservicenumber;
    }

    public void setFreeservicenumber(StringFilter freeservicenumber) {
        this.freeservicenumber = freeservicenumber;
    }

    public IntegerFilter getCompanyid() {
        return companyid;
    }

    public Optional<IntegerFilter> optionalCompanyid() {
        return Optional.ofNullable(companyid);
    }

    public IntegerFilter companyid() {
        if (companyid == null) {
            setCompanyid(new IntegerFilter());
        }
        return companyid;
    }

    public void setCompanyid(IntegerFilter companyid) {
        this.companyid = companyid;
    }

    public BooleanFilter getUpdatetocustomer() {
        return updatetocustomer;
    }

    public Optional<BooleanFilter> optionalUpdatetocustomer() {
        return Optional.ofNullable(updatetocustomer);
    }

    public BooleanFilter updatetocustomer() {
        if (updatetocustomer == null) {
            setUpdatetocustomer(new BooleanFilter());
        }
        return updatetocustomer;
    }

    public void setUpdatetocustomer(BooleanFilter updatetocustomer) {
        this.updatetocustomer = updatetocustomer;
    }

    public StringFilter getNextgearoilmilage() {
        return nextgearoilmilage;
    }

    public Optional<StringFilter> optionalNextgearoilmilage() {
        return Optional.ofNullable(nextgearoilmilage);
    }

    public StringFilter nextgearoilmilage() {
        if (nextgearoilmilage == null) {
            setNextgearoilmilage(new StringFilter());
        }
        return nextgearoilmilage;
    }

    public void setNextgearoilmilage(StringFilter nextgearoilmilage) {
        this.nextgearoilmilage = nextgearoilmilage;
    }

    public BooleanFilter getIsjobinvoiced() {
        return isjobinvoiced;
    }

    public Optional<BooleanFilter> optionalIsjobinvoiced() {
        return Optional.ofNullable(isjobinvoiced);
    }

    public BooleanFilter isjobinvoiced() {
        if (isjobinvoiced == null) {
            setIsjobinvoiced(new BooleanFilter());
        }
        return isjobinvoiced;
    }

    public void setIsjobinvoiced(BooleanFilter isjobinvoiced) {
        this.isjobinvoiced = isjobinvoiced;
    }

    public BooleanFilter getIswaiting() {
        return iswaiting;
    }

    public Optional<BooleanFilter> optionalIswaiting() {
        return Optional.ofNullable(iswaiting);
    }

    public BooleanFilter iswaiting() {
        if (iswaiting == null) {
            setIswaiting(new BooleanFilter());
        }
        return iswaiting;
    }

    public void setIswaiting(BooleanFilter iswaiting) {
        this.iswaiting = iswaiting;
    }

    public BooleanFilter getIscustomercomment() {
        return iscustomercomment;
    }

    public Optional<BooleanFilter> optionalIscustomercomment() {
        return Optional.ofNullable(iscustomercomment);
    }

    public BooleanFilter iscustomercomment() {
        if (iscustomercomment == null) {
            setIscustomercomment(new BooleanFilter());
        }
        return iscustomercomment;
    }

    public void setIscustomercomment(BooleanFilter iscustomercomment) {
        this.iscustomercomment = iscustomercomment;
    }

    public StringFilter getImagefolder() {
        return imagefolder;
    }

    public Optional<StringFilter> optionalImagefolder() {
        return Optional.ofNullable(imagefolder);
    }

    public StringFilter imagefolder() {
        if (imagefolder == null) {
            setImagefolder(new StringFilter());
        }
        return imagefolder;
    }

    public void setImagefolder(StringFilter imagefolder) {
        this.imagefolder = imagefolder;
    }

    public StringFilter getFrontimage() {
        return frontimage;
    }

    public Optional<StringFilter> optionalFrontimage() {
        return Optional.ofNullable(frontimage);
    }

    public StringFilter frontimage() {
        if (frontimage == null) {
            setFrontimage(new StringFilter());
        }
        return frontimage;
    }

    public void setFrontimage(StringFilter frontimage) {
        this.frontimage = frontimage;
    }

    public StringFilter getLeftimage() {
        return leftimage;
    }

    public Optional<StringFilter> optionalLeftimage() {
        return Optional.ofNullable(leftimage);
    }

    public StringFilter leftimage() {
        if (leftimage == null) {
            setLeftimage(new StringFilter());
        }
        return leftimage;
    }

    public void setLeftimage(StringFilter leftimage) {
        this.leftimage = leftimage;
    }

    public StringFilter getRightimage() {
        return rightimage;
    }

    public Optional<StringFilter> optionalRightimage() {
        return Optional.ofNullable(rightimage);
    }

    public StringFilter rightimage() {
        if (rightimage == null) {
            setRightimage(new StringFilter());
        }
        return rightimage;
    }

    public void setRightimage(StringFilter rightimage) {
        this.rightimage = rightimage;
    }

    public StringFilter getBackimage() {
        return backimage;
    }

    public Optional<StringFilter> optionalBackimage() {
        return Optional.ofNullable(backimage);
    }

    public StringFilter backimage() {
        if (backimage == null) {
            setBackimage(new StringFilter());
        }
        return backimage;
    }

    public void setBackimage(StringFilter backimage) {
        this.backimage = backimage;
    }

    public StringFilter getDashboardimage() {
        return dashboardimage;
    }

    public Optional<StringFilter> optionalDashboardimage() {
        return Optional.ofNullable(dashboardimage);
    }

    public StringFilter dashboardimage() {
        if (dashboardimage == null) {
            setDashboardimage(new StringFilter());
        }
        return dashboardimage;
    }

    public void setDashboardimage(StringFilter dashboardimage) {
        this.dashboardimage = dashboardimage;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AutocarejobCriteria that = (AutocarejobCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(jobnumber, that.jobnumber) &&
            Objects.equals(vehicleid, that.vehicleid) &&
            Objects.equals(vehiclenumber, that.vehiclenumber) &&
            Objects.equals(millage, that.millage) &&
            Objects.equals(nextmillage, that.nextmillage) &&
            Objects.equals(nextservicedate, that.nextservicedate) &&
            Objects.equals(vehicletypeid, that.vehicletypeid) &&
            Objects.equals(jobtypeid, that.jobtypeid) &&
            Objects.equals(jobtypename, that.jobtypename) &&
            Objects.equals(jobopenby, that.jobopenby) &&
            Objects.equals(jobopentime, that.jobopentime) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(specialrquirments, that.specialrquirments) &&
            Objects.equals(specialinstructions, that.specialinstructions) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(nextserviceinstructions, that.nextserviceinstructions) &&
            Objects.equals(lastserviceinstructions, that.lastserviceinstructions) &&
            Objects.equals(isadvisorchecked, that.isadvisorchecked) &&
            Objects.equals(isempallocated, that.isempallocated) &&
            Objects.equals(jobclosetime, that.jobclosetime) &&
            Objects.equals(isjobclose, that.isjobclose) &&
            Objects.equals(isfeedback, that.isfeedback) &&
            Objects.equals(feedbackstatusid, that.feedbackstatusid) &&
            Objects.equals(customername, that.customername) &&
            Objects.equals(customertel, that.customertel) &&
            Objects.equals(customerid, that.customerid) &&
            Objects.equals(advisorfinalcheck, that.advisorfinalcheck) &&
            Objects.equals(jobdate, that.jobdate) &&
            Objects.equals(iscompanyservice, that.iscompanyservice) &&
            Objects.equals(freeservicenumber, that.freeservicenumber) &&
            Objects.equals(companyid, that.companyid) &&
            Objects.equals(updatetocustomer, that.updatetocustomer) &&
            Objects.equals(nextgearoilmilage, that.nextgearoilmilage) &&
            Objects.equals(isjobinvoiced, that.isjobinvoiced) &&
            Objects.equals(iswaiting, that.iswaiting) &&
            Objects.equals(iscustomercomment, that.iscustomercomment) &&
            Objects.equals(imagefolder, that.imagefolder) &&
            Objects.equals(frontimage, that.frontimage) &&
            Objects.equals(leftimage, that.leftimage) &&
            Objects.equals(rightimage, that.rightimage) &&
            Objects.equals(backimage, that.backimage) &&
            Objects.equals(dashboardimage, that.dashboardimage) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            jobnumber,
            vehicleid,
            vehiclenumber,
            millage,
            nextmillage,
            nextservicedate,
            vehicletypeid,
            jobtypeid,
            jobtypename,
            jobopenby,
            jobopentime,
            lmu,
            lmd,
            specialrquirments,
            specialinstructions,
            remarks,
            nextserviceinstructions,
            lastserviceinstructions,
            isadvisorchecked,
            isempallocated,
            jobclosetime,
            isjobclose,
            isfeedback,
            feedbackstatusid,
            customername,
            customertel,
            customerid,
            advisorfinalcheck,
            jobdate,
            iscompanyservice,
            freeservicenumber,
            companyid,
            updatetocustomer,
            nextgearoilmilage,
            isjobinvoiced,
            iswaiting,
            iscustomercomment,
            imagefolder,
            frontimage,
            leftimage,
            rightimage,
            backimage,
            dashboardimage,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutocarejobCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalJobnumber().map(f -> "jobnumber=" + f + ", ").orElse("") +
            optionalVehicleid().map(f -> "vehicleid=" + f + ", ").orElse("") +
            optionalVehiclenumber().map(f -> "vehiclenumber=" + f + ", ").orElse("") +
            optionalMillage().map(f -> "millage=" + f + ", ").orElse("") +
            optionalNextmillage().map(f -> "nextmillage=" + f + ", ").orElse("") +
            optionalNextservicedate().map(f -> "nextservicedate=" + f + ", ").orElse("") +
            optionalVehicletypeid().map(f -> "vehicletypeid=" + f + ", ").orElse("") +
            optionalJobtypeid().map(f -> "jobtypeid=" + f + ", ").orElse("") +
            optionalJobtypename().map(f -> "jobtypename=" + f + ", ").orElse("") +
            optionalJobopenby().map(f -> "jobopenby=" + f + ", ").orElse("") +
            optionalJobopentime().map(f -> "jobopentime=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalSpecialrquirments().map(f -> "specialrquirments=" + f + ", ").orElse("") +
            optionalSpecialinstructions().map(f -> "specialinstructions=" + f + ", ").orElse("") +
            optionalRemarks().map(f -> "remarks=" + f + ", ").orElse("") +
            optionalNextserviceinstructions().map(f -> "nextserviceinstructions=" + f + ", ").orElse("") +
            optionalLastserviceinstructions().map(f -> "lastserviceinstructions=" + f + ", ").orElse("") +
            optionalIsadvisorchecked().map(f -> "isadvisorchecked=" + f + ", ").orElse("") +
            optionalIsempallocated().map(f -> "isempallocated=" + f + ", ").orElse("") +
            optionalJobclosetime().map(f -> "jobclosetime=" + f + ", ").orElse("") +
            optionalIsjobclose().map(f -> "isjobclose=" + f + ", ").orElse("") +
            optionalIsfeedback().map(f -> "isfeedback=" + f + ", ").orElse("") +
            optionalFeedbackstatusid().map(f -> "feedbackstatusid=" + f + ", ").orElse("") +
            optionalCustomername().map(f -> "customername=" + f + ", ").orElse("") +
            optionalCustomertel().map(f -> "customertel=" + f + ", ").orElse("") +
            optionalCustomerid().map(f -> "customerid=" + f + ", ").orElse("") +
            optionalAdvisorfinalcheck().map(f -> "advisorfinalcheck=" + f + ", ").orElse("") +
            optionalJobdate().map(f -> "jobdate=" + f + ", ").orElse("") +
            optionalIscompanyservice().map(f -> "iscompanyservice=" + f + ", ").orElse("") +
            optionalFreeservicenumber().map(f -> "freeservicenumber=" + f + ", ").orElse("") +
            optionalCompanyid().map(f -> "companyid=" + f + ", ").orElse("") +
            optionalUpdatetocustomer().map(f -> "updatetocustomer=" + f + ", ").orElse("") +
            optionalNextgearoilmilage().map(f -> "nextgearoilmilage=" + f + ", ").orElse("") +
            optionalIsjobinvoiced().map(f -> "isjobinvoiced=" + f + ", ").orElse("") +
            optionalIswaiting().map(f -> "iswaiting=" + f + ", ").orElse("") +
            optionalIscustomercomment().map(f -> "iscustomercomment=" + f + ", ").orElse("") +
            optionalImagefolder().map(f -> "imagefolder=" + f + ", ").orElse("") +
            optionalFrontimage().map(f -> "frontimage=" + f + ", ").orElse("") +
            optionalLeftimage().map(f -> "leftimage=" + f + ", ").orElse("") +
            optionalRightimage().map(f -> "rightimage=" + f + ", ").orElse("") +
            optionalBackimage().map(f -> "backimage=" + f + ", ").orElse("") +
            optionalDashboardimage().map(f -> "dashboardimage=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
