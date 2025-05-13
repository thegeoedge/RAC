package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Autocarejob;
import com.heavenscode.rac.repository.AutocarejobRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Autocarejob}.
 */
@Service
@Transactional
public class AutocarejobService {

    private static final Logger LOG = LoggerFactory.getLogger(AutocarejobService.class);

    private final AutocarejobRepository autocarejobRepository;

    public AutocarejobService(AutocarejobRepository autocarejobRepository) {
        this.autocarejobRepository = autocarejobRepository;
    }

    /**
     * Save a autocarejob.
     *
     * @param autocarejob the entity to save.
     * @return the persisted entity.
     */
    public Autocarejob save(Autocarejob autocarejob) {
        LOG.debug("Request to save Autocarejob : {}", autocarejob);
        return autocarejobRepository.save(autocarejob);
    }

    /**
     * Update a autocarejob.
     *
     * @param autocarejob the entity to save.
     * @return the persisted entity.
     */
    public Autocarejob update(Autocarejob autocarejob) {
        LOG.debug("Request to update Autocarejob : {}", autocarejob);
        return autocarejobRepository.save(autocarejob);
    }

    /**
     * Partially update a autocarejob.
     *
     * @param autocarejob the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Autocarejob> partialUpdate(Autocarejob autocarejob) {
        LOG.debug("Request to partially update Autocarejob : {}", autocarejob);

        return autocarejobRepository
            .findById(autocarejob.getId())
            .map(existingAutocarejob -> {
                if (autocarejob.getJobnumber() != null) {
                    existingAutocarejob.setJobnumber(autocarejob.getJobnumber());
                }
                if (autocarejob.getVehicleid() != null) {
                    existingAutocarejob.setVehicleid(autocarejob.getVehicleid());
                }
                if (autocarejob.getVehiclenumber() != null) {
                    existingAutocarejob.setVehiclenumber(autocarejob.getVehiclenumber());
                }
                if (autocarejob.getMillage() != null) {
                    existingAutocarejob.setMillage(autocarejob.getMillage());
                }
                if (autocarejob.getNextmillage() != null) {
                    existingAutocarejob.setNextmillage(autocarejob.getNextmillage());
                }
                if (autocarejob.getNextservicedate() != null) {
                    existingAutocarejob.setNextservicedate(autocarejob.getNextservicedate());
                }
                if (autocarejob.getVehicletypeid() != null) {
                    existingAutocarejob.setVehicletypeid(autocarejob.getVehicletypeid());
                }
                if (autocarejob.getJobtypeid() != null) {
                    existingAutocarejob.setJobtypeid(autocarejob.getJobtypeid());
                }
                if (autocarejob.getJobtypename() != null) {
                    existingAutocarejob.setJobtypename(autocarejob.getJobtypename());
                }
                if (autocarejob.getJobopenby() != null) {
                    existingAutocarejob.setJobopenby(autocarejob.getJobopenby());
                }
                if (autocarejob.getJobopentime() != null) {
                    existingAutocarejob.setJobopentime(autocarejob.getJobopentime());
                }
                if (autocarejob.getLmu() != null) {
                    existingAutocarejob.setLmu(autocarejob.getLmu());
                }
                if (autocarejob.getLmd() != null) {
                    existingAutocarejob.setLmd(autocarejob.getLmd());
                }
                if (autocarejob.getSpecialrquirments() != null) {
                    existingAutocarejob.setSpecialrquirments(autocarejob.getSpecialrquirments());
                }
                if (autocarejob.getSpecialinstructions() != null) {
                    existingAutocarejob.setSpecialinstructions(autocarejob.getSpecialinstructions());
                }
                if (autocarejob.getRemarks() != null) {
                    existingAutocarejob.setRemarks(autocarejob.getRemarks());
                }
                if (autocarejob.getNextserviceinstructions() != null) {
                    existingAutocarejob.setNextserviceinstructions(autocarejob.getNextserviceinstructions());
                }
                if (autocarejob.getLastserviceinstructions() != null) {
                    existingAutocarejob.setLastserviceinstructions(autocarejob.getLastserviceinstructions());
                }
                if (autocarejob.getIsadvisorchecked() != null) {
                    existingAutocarejob.setIsadvisorchecked(autocarejob.getIsadvisorchecked());
                }
                if (autocarejob.getIsempallocated() != null) {
                    existingAutocarejob.setIsempallocated(autocarejob.getIsempallocated());
                }
                if (autocarejob.getJobclosetime() != null) {
                    existingAutocarejob.setJobclosetime(autocarejob.getJobclosetime());
                }
                if (autocarejob.getIsjobclose() != null) {
                    existingAutocarejob.setIsjobclose(autocarejob.getIsjobclose());
                }
                if (autocarejob.getIsfeedback() != null) {
                    existingAutocarejob.setIsfeedback(autocarejob.getIsfeedback());
                }
                if (autocarejob.getFeedbackstatusid() != null) {
                    existingAutocarejob.setFeedbackstatusid(autocarejob.getFeedbackstatusid());
                }
                if (autocarejob.getCustomername() != null) {
                    existingAutocarejob.setCustomername(autocarejob.getCustomername());
                }
                if (autocarejob.getCustomertel() != null) {
                    existingAutocarejob.setCustomertel(autocarejob.getCustomertel());
                }
                if (autocarejob.getCustomerid() != null) {
                    existingAutocarejob.setCustomerid(autocarejob.getCustomerid());
                }
                if (autocarejob.getAdvisorfinalcheck() != null) {
                    existingAutocarejob.setAdvisorfinalcheck(autocarejob.getAdvisorfinalcheck());
                }
                if (autocarejob.getJobdate() != null) {
                    existingAutocarejob.setJobdate(autocarejob.getJobdate());
                }
                if (autocarejob.getIscompanyservice() != null) {
                    existingAutocarejob.setIscompanyservice(autocarejob.getIscompanyservice());
                }
                if (autocarejob.getFreeservicenumber() != null) {
                    existingAutocarejob.setFreeservicenumber(autocarejob.getFreeservicenumber());
                }
                if (autocarejob.getCompanyid() != null) {
                    existingAutocarejob.setCompanyid(autocarejob.getCompanyid());
                }
                if (autocarejob.getUpdatetocustomer() != null) {
                    existingAutocarejob.setUpdatetocustomer(autocarejob.getUpdatetocustomer());
                }
                if (autocarejob.getNextgearoilmilage() != null) {
                    existingAutocarejob.setNextgearoilmilage(autocarejob.getNextgearoilmilage());
                }
                if (autocarejob.getIsjobinvoiced() != null) {
                    existingAutocarejob.setIsjobinvoiced(autocarejob.getIsjobinvoiced());
                }
                if (autocarejob.getIswaiting() != null) {
                    existingAutocarejob.setIswaiting(autocarejob.getIswaiting());
                }
                if (autocarejob.getIscustomercomment() != null) {
                    existingAutocarejob.setIscustomercomment(autocarejob.getIscustomercomment());
                }
                if (autocarejob.getImagefolder() != null) {
                    existingAutocarejob.setImagefolder(autocarejob.getImagefolder());
                }
                if (autocarejob.getFrontimage() != null) {
                    existingAutocarejob.setFrontimage(autocarejob.getFrontimage());
                }
                if (autocarejob.getLeftimage() != null) {
                    existingAutocarejob.setLeftimage(autocarejob.getLeftimage());
                }
                if (autocarejob.getRightimage() != null) {
                    existingAutocarejob.setRightimage(autocarejob.getRightimage());
                }
                if (autocarejob.getBackimage() != null) {
                    existingAutocarejob.setBackimage(autocarejob.getBackimage());
                }
                if (autocarejob.getDashboardimage() != null) {
                    existingAutocarejob.setDashboardimage(autocarejob.getDashboardimage());
                }

                return existingAutocarejob;
            })
            .map(autocarejobRepository::save);
    }

    /**
     * Get one autocarejob by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Autocarejob> findOne(Long id) {
        LOG.debug("Request to get Autocarejob : {}", id);
        return autocarejobRepository.findById(id);
    }

    /**
     * Delete the autocarejob by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Autocarejob : {}", id);
        autocarejobRepository.deleteById(id);
    }
}
