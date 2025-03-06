package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.SystemSettings;
import com.heavenscode.rac.repository.SystemSettingsRepository;
import com.heavenscode.rac.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.heavenscode.rac.domain.SystemSettings}.
 */
@RestController
@RequestMapping("/api/system-settings")
@Transactional
public class SystemSettingsResource {

    private static final Logger LOG = LoggerFactory.getLogger(SystemSettingsResource.class);

    private static final String ENTITY_NAME = "systemSettings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemSettingsRepository systemSettingsRepository;

    public SystemSettingsResource(SystemSettingsRepository systemSettingsRepository) {
        this.systemSettingsRepository = systemSettingsRepository;
    }

    /**
     * {@code POST  /system-settings} : Create a new systemSettings.
     *
     * @param systemSettings the systemSettings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemSettings, or with status {@code 400 (Bad Request)} if the systemSettings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SystemSettings> createSystemSettings(@RequestBody SystemSettings systemSettings) throws URISyntaxException {
        LOG.debug("REST request to save SystemSettings : {}", systemSettings);
        if (systemSettings.getId() != null) {
            throw new BadRequestAlertException("A new systemSettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        systemSettings = systemSettingsRepository.save(systemSettings);
        return ResponseEntity.created(new URI("/api/system-settings/" + systemSettings.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, systemSettings.getId().toString()))
            .body(systemSettings);
    }

    /**
     * {@code PUT  /system-settings/:id} : Updates an existing systemSettings.
     *
     * @param id the id of the systemSettings to save.
     * @param systemSettings the systemSettings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemSettings,
     * or with status {@code 400 (Bad Request)} if the systemSettings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemSettings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SystemSettings> updateSystemSettings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SystemSettings systemSettings
    ) throws URISyntaxException {
        LOG.debug("REST request to update SystemSettings : {}, {}", id, systemSettings);
        if (systemSettings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, systemSettings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!systemSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        systemSettings = systemSettingsRepository.save(systemSettings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, systemSettings.getId().toString()))
            .body(systemSettings);
    }

    /**
     * {@code PATCH  /system-settings/:id} : Partial updates given fields of an existing systemSettings, field will ignore if it is null
     *
     * @param id the id of the systemSettings to save.
     * @param systemSettings the systemSettings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemSettings,
     * or with status {@code 400 (Bad Request)} if the systemSettings is not valid,
     * or with status {@code 404 (Not Found)} if the systemSettings is not found,
     * or with status {@code 500 (Internal Server Error)} if the systemSettings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SystemSettings> partialUpdateSystemSettings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SystemSettings systemSettings
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SystemSettings partially : {}, {}", id, systemSettings);
        if (systemSettings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, systemSettings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!systemSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SystemSettings> result = systemSettingsRepository
            .findById(systemSettings.getId())
            .map(existingSystemSettings -> {
                if (systemSettings.getKey() != null) {
                    existingSystemSettings.setKey(systemSettings.getKey());
                }
                if (systemSettings.getLastValue() != null) {
                    existingSystemSettings.setLastValue(systemSettings.getLastValue());
                }
                if (systemSettings.getNextValue() != null) {
                    existingSystemSettings.setNextValue(systemSettings.getNextValue());
                }
                if (systemSettings.getLmu() != null) {
                    existingSystemSettings.setLmu(systemSettings.getLmu());
                }
                if (systemSettings.getLmd() != null) {
                    existingSystemSettings.setLmd(systemSettings.getLmd());
                }

                return existingSystemSettings;
            })
            .map(systemSettingsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, systemSettings.getId().toString())
        );
    }

    /**
     * {@code GET  /system-settings} : get all the systemSettings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systemSettings in body.
     */
    @GetMapping("")
    public List<SystemSettings> getAllSystemSettings() {
        LOG.debug("REST request to get all SystemSettings");
        return systemSettingsRepository.findAll();
    }

    /**
     * {@code GET  /system-settings/:id} : get the "id" systemSettings.
     *
     * @param id the id of the systemSettings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemSettings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SystemSettings> getSystemSettings(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SystemSettings : {}", id);
        Optional<SystemSettings> systemSettings = systemSettingsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(systemSettings);
    }

    /**
     * {@code DELETE  /system-settings/:id} : delete the "id" systemSettings.
     *
     * @param id the id of the systemSettings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSystemSettings(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SystemSettings : {}", id);
        systemSettingsRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
