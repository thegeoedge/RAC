package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.AutocarejobAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Autocarejob;
import com.heavenscode.rac.repository.AutocarejobRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AutocarejobResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutocarejobResourceIT {

    private static final Integer DEFAULT_JOBNUMBER = 1;
    private static final Integer UPDATED_JOBNUMBER = 2;
    private static final Integer SMALLER_JOBNUMBER = 1 - 1;

    private static final Integer DEFAULT_VEHICLEID = 1;
    private static final Integer UPDATED_VEHICLEID = 2;
    private static final Integer SMALLER_VEHICLEID = 1 - 1;

    private static final String DEFAULT_VEHICLENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLENUMBER = "BBBBBBBBBB";

    private static final Float DEFAULT_MILLAGE = 1F;
    private static final Float UPDATED_MILLAGE = 2F;
    private static final Float SMALLER_MILLAGE = 1F - 1F;

    private static final Float DEFAULT_NEXTMILLAGE = 1F;
    private static final Float UPDATED_NEXTMILLAGE = 2F;
    private static final Float SMALLER_NEXTMILLAGE = 1F - 1F;

    private static final LocalDate DEFAULT_NEXTSERVICEDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NEXTSERVICEDATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_NEXTSERVICEDATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_VEHICLETYPEID = 1;
    private static final Integer UPDATED_VEHICLETYPEID = 2;
    private static final Integer SMALLER_VEHICLETYPEID = 1 - 1;

    private static final Integer DEFAULT_JOBTYPEID = 1;
    private static final Integer UPDATED_JOBTYPEID = 2;
    private static final Integer SMALLER_JOBTYPEID = 1 - 1;

    private static final String DEFAULT_JOBTYPENAME = "AAAAAAAAAA";
    private static final String UPDATED_JOBTYPENAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_JOBOPENBY = 1;
    private static final Integer UPDATED_JOBOPENBY = 2;
    private static final Integer SMALLER_JOBOPENBY = 1 - 1;

    private static final Instant DEFAULT_JOBOPENTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JOBOPENTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SPECIALRQUIRMENTS = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALRQUIRMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALINSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALINSTRUCTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_NEXTSERVICEINSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_NEXTSERVICEINSTRUCTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_LASTSERVICEINSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_LASTSERVICEINSTRUCTIONS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISADVISORCHECKED = false;
    private static final Boolean UPDATED_ISADVISORCHECKED = true;

    private static final Boolean DEFAULT_ISEMPALLOCATED = false;
    private static final Boolean UPDATED_ISEMPALLOCATED = true;

    private static final Instant DEFAULT_JOBCLOSETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JOBCLOSETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ISJOBCLOSE = false;
    private static final Boolean UPDATED_ISJOBCLOSE = true;

    private static final Boolean DEFAULT_ISFEEDBACK = false;
    private static final Boolean UPDATED_ISFEEDBACK = true;

    private static final Integer DEFAULT_FEEDBACKSTATUSID = 1;
    private static final Integer UPDATED_FEEDBACKSTATUSID = 2;
    private static final Integer SMALLER_FEEDBACKSTATUSID = 1 - 1;

    private static final String DEFAULT_CUSTOMERNAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMERTEL = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMERTEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMERID = 1;
    private static final Integer UPDATED_CUSTOMERID = 2;
    private static final Integer SMALLER_CUSTOMERID = 1 - 1;

    private static final Boolean DEFAULT_ADVISORFINALCHECK = false;
    private static final Boolean UPDATED_ADVISORFINALCHECK = true;

    private static final Instant DEFAULT_JOBDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JOBDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ISCOMPANYSERVICE = false;
    private static final Boolean UPDATED_ISCOMPANYSERVICE = true;

    private static final String DEFAULT_FREESERVICENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FREESERVICENUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_COMPANYID = 1;
    private static final Integer UPDATED_COMPANYID = 2;
    private static final Integer SMALLER_COMPANYID = 1 - 1;

    private static final Boolean DEFAULT_UPDATETOCUSTOMER = false;
    private static final Boolean UPDATED_UPDATETOCUSTOMER = true;

    private static final String DEFAULT_NEXTGEAROILMILAGE = "AAAAAAAAAA";
    private static final String UPDATED_NEXTGEAROILMILAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISJOBINVOICED = false;
    private static final Boolean UPDATED_ISJOBINVOICED = true;

    private static final Boolean DEFAULT_ISWAITING = false;
    private static final Boolean UPDATED_ISWAITING = true;

    private static final Boolean DEFAULT_ISCUSTOMERCOMMENT = false;
    private static final Boolean UPDATED_ISCUSTOMERCOMMENT = true;

    private static final String DEFAULT_IMAGEFOLDER = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEFOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_FRONTIMAGE = "AAAAAAAAAA";
    private static final String UPDATED_FRONTIMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_LEFTIMAGE = "AAAAAAAAAA";
    private static final String UPDATED_LEFTIMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_RIGHTIMAGE = "AAAAAAAAAA";
    private static final String UPDATED_RIGHTIMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_BACKIMAGE = "AAAAAAAAAA";
    private static final String UPDATED_BACKIMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_DASHBOARDIMAGE = "AAAAAAAAAA";
    private static final String UPDATED_DASHBOARDIMAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/autocarejobs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AutocarejobRepository autocarejobRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutocarejobMockMvc;

    private Autocarejob autocarejob;

    private Autocarejob insertedAutocarejob;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autocarejob createEntity() {
        return new Autocarejob()
            .jobnumber(DEFAULT_JOBNUMBER)
            .vehicleid(DEFAULT_VEHICLEID)
            .vehiclenumber(DEFAULT_VEHICLENUMBER)
            .millage(DEFAULT_MILLAGE)
            .nextmillage(DEFAULT_NEXTMILLAGE)
            .nextservicedate(DEFAULT_NEXTSERVICEDATE)
            .vehicletypeid(DEFAULT_VEHICLETYPEID)
            .jobtypeid(DEFAULT_JOBTYPEID)
            .jobtypename(DEFAULT_JOBTYPENAME)
            .jobopenby(DEFAULT_JOBOPENBY)
            .jobopentime(DEFAULT_JOBOPENTIME)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .specialrquirments(DEFAULT_SPECIALRQUIRMENTS)
            .specialinstructions(DEFAULT_SPECIALINSTRUCTIONS)
            .remarks(DEFAULT_REMARKS)
            .nextserviceinstructions(DEFAULT_NEXTSERVICEINSTRUCTIONS)
            .lastserviceinstructions(DEFAULT_LASTSERVICEINSTRUCTIONS)
            .isadvisorchecked(DEFAULT_ISADVISORCHECKED)
            .isempallocated(DEFAULT_ISEMPALLOCATED)
            .jobclosetime(DEFAULT_JOBCLOSETIME)
            .isjobclose(DEFAULT_ISJOBCLOSE)
            .isfeedback(DEFAULT_ISFEEDBACK)
            .feedbackstatusid(DEFAULT_FEEDBACKSTATUSID)
            .customername(DEFAULT_CUSTOMERNAME)
            .customertel(DEFAULT_CUSTOMERTEL)
            .customerid(DEFAULT_CUSTOMERID)
            .advisorfinalcheck(DEFAULT_ADVISORFINALCHECK)
            .jobdate(DEFAULT_JOBDATE)
            .iscompanyservice(DEFAULT_ISCOMPANYSERVICE)
            .freeservicenumber(DEFAULT_FREESERVICENUMBER)
            .companyid(DEFAULT_COMPANYID)
            .updatetocustomer(DEFAULT_UPDATETOCUSTOMER)
            .nextgearoilmilage(DEFAULT_NEXTGEAROILMILAGE)
            .isjobinvoiced(DEFAULT_ISJOBINVOICED)
            .iswaiting(DEFAULT_ISWAITING)
            .iscustomercomment(DEFAULT_ISCUSTOMERCOMMENT)
            .imagefolder(DEFAULT_IMAGEFOLDER)
            .frontimage(DEFAULT_FRONTIMAGE)
            .leftimage(DEFAULT_LEFTIMAGE)
            .rightimage(DEFAULT_RIGHTIMAGE)
            .backimage(DEFAULT_BACKIMAGE)
            .dashboardimage(DEFAULT_DASHBOARDIMAGE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autocarejob createUpdatedEntity() {
        return new Autocarejob()
            .jobnumber(UPDATED_JOBNUMBER)
            .vehicleid(UPDATED_VEHICLEID)
            .vehiclenumber(UPDATED_VEHICLENUMBER)
            .millage(UPDATED_MILLAGE)
            .nextmillage(UPDATED_NEXTMILLAGE)
            .nextservicedate(UPDATED_NEXTSERVICEDATE)
            .vehicletypeid(UPDATED_VEHICLETYPEID)
            .jobtypeid(UPDATED_JOBTYPEID)
            .jobtypename(UPDATED_JOBTYPENAME)
            .jobopenby(UPDATED_JOBOPENBY)
            .jobopentime(UPDATED_JOBOPENTIME)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .specialrquirments(UPDATED_SPECIALRQUIRMENTS)
            .specialinstructions(UPDATED_SPECIALINSTRUCTIONS)
            .remarks(UPDATED_REMARKS)
            .nextserviceinstructions(UPDATED_NEXTSERVICEINSTRUCTIONS)
            .lastserviceinstructions(UPDATED_LASTSERVICEINSTRUCTIONS)
            .isadvisorchecked(UPDATED_ISADVISORCHECKED)
            .isempallocated(UPDATED_ISEMPALLOCATED)
            .jobclosetime(UPDATED_JOBCLOSETIME)
            .isjobclose(UPDATED_ISJOBCLOSE)
            .isfeedback(UPDATED_ISFEEDBACK)
            .feedbackstatusid(UPDATED_FEEDBACKSTATUSID)
            .customername(UPDATED_CUSTOMERNAME)
            .customertel(UPDATED_CUSTOMERTEL)
            .customerid(UPDATED_CUSTOMERID)
            .advisorfinalcheck(UPDATED_ADVISORFINALCHECK)
            .jobdate(UPDATED_JOBDATE)
            .iscompanyservice(UPDATED_ISCOMPANYSERVICE)
            .freeservicenumber(UPDATED_FREESERVICENUMBER)
            .companyid(UPDATED_COMPANYID)
            .updatetocustomer(UPDATED_UPDATETOCUSTOMER)
            .nextgearoilmilage(UPDATED_NEXTGEAROILMILAGE)
            .isjobinvoiced(UPDATED_ISJOBINVOICED)
            .iswaiting(UPDATED_ISWAITING)
            .iscustomercomment(UPDATED_ISCUSTOMERCOMMENT)
            .imagefolder(UPDATED_IMAGEFOLDER)
            .frontimage(UPDATED_FRONTIMAGE)
            .leftimage(UPDATED_LEFTIMAGE)
            .rightimage(UPDATED_RIGHTIMAGE)
            .backimage(UPDATED_BACKIMAGE)
            .dashboardimage(UPDATED_DASHBOARDIMAGE);
    }

    @BeforeEach
    public void initTest() {
        autocarejob = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAutocarejob != null) {
            autocarejobRepository.delete(insertedAutocarejob);
            insertedAutocarejob = null;
        }
    }

    @Test
    @Transactional
    void createAutocarejob() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Autocarejob
        var returnedAutocarejob = om.readValue(
            restAutocarejobMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autocarejob)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Autocarejob.class
        );

        // Validate the Autocarejob in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAutocarejobUpdatableFieldsEquals(returnedAutocarejob, getPersistedAutocarejob(returnedAutocarejob));

        insertedAutocarejob = returnedAutocarejob;
    }

    @Test
    @Transactional
    void createAutocarejobWithExistingId() throws Exception {
        // Create the Autocarejob with an existing ID
        autocarejob.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutocarejobMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autocarejob)))
            .andExpect(status().isBadRequest());

        // Validate the Autocarejob in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAutocarejobs() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList
        restAutocarejobMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autocarejob.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobnumber").value(hasItem(DEFAULT_JOBNUMBER)))
            .andExpect(jsonPath("$.[*].vehicleid").value(hasItem(DEFAULT_VEHICLEID)))
            .andExpect(jsonPath("$.[*].vehiclenumber").value(hasItem(DEFAULT_VEHICLENUMBER)))
            .andExpect(jsonPath("$.[*].millage").value(hasItem(DEFAULT_MILLAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].nextmillage").value(hasItem(DEFAULT_NEXTMILLAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].nextservicedate").value(hasItem(DEFAULT_NEXTSERVICEDATE.toString())))
            .andExpect(jsonPath("$.[*].vehicletypeid").value(hasItem(DEFAULT_VEHICLETYPEID)))
            .andExpect(jsonPath("$.[*].jobtypeid").value(hasItem(DEFAULT_JOBTYPEID)))
            .andExpect(jsonPath("$.[*].jobtypename").value(hasItem(DEFAULT_JOBTYPENAME)))
            .andExpect(jsonPath("$.[*].jobopenby").value(hasItem(DEFAULT_JOBOPENBY)))
            .andExpect(jsonPath("$.[*].jobopentime").value(hasItem(DEFAULT_JOBOPENTIME.toString())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].specialrquirments").value(hasItem(DEFAULT_SPECIALRQUIRMENTS)))
            .andExpect(jsonPath("$.[*].specialinstructions").value(hasItem(DEFAULT_SPECIALINSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].nextserviceinstructions").value(hasItem(DEFAULT_NEXTSERVICEINSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].lastserviceinstructions").value(hasItem(DEFAULT_LASTSERVICEINSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].isadvisorchecked").value(hasItem(DEFAULT_ISADVISORCHECKED)))
            .andExpect(jsonPath("$.[*].isempallocated").value(hasItem(DEFAULT_ISEMPALLOCATED)))
            .andExpect(jsonPath("$.[*].jobclosetime").value(hasItem(DEFAULT_JOBCLOSETIME.toString())))
            .andExpect(jsonPath("$.[*].isjobclose").value(hasItem(DEFAULT_ISJOBCLOSE)))
            .andExpect(jsonPath("$.[*].isfeedback").value(hasItem(DEFAULT_ISFEEDBACK)))
            .andExpect(jsonPath("$.[*].feedbackstatusid").value(hasItem(DEFAULT_FEEDBACKSTATUSID)))
            .andExpect(jsonPath("$.[*].customername").value(hasItem(DEFAULT_CUSTOMERNAME)))
            .andExpect(jsonPath("$.[*].customertel").value(hasItem(DEFAULT_CUSTOMERTEL)))
            .andExpect(jsonPath("$.[*].customerid").value(hasItem(DEFAULT_CUSTOMERID)))
            .andExpect(jsonPath("$.[*].advisorfinalcheck").value(hasItem(DEFAULT_ADVISORFINALCHECK)))
            .andExpect(jsonPath("$.[*].jobdate").value(hasItem(DEFAULT_JOBDATE.toString())))
            .andExpect(jsonPath("$.[*].iscompanyservice").value(hasItem(DEFAULT_ISCOMPANYSERVICE)))
            .andExpect(jsonPath("$.[*].freeservicenumber").value(hasItem(DEFAULT_FREESERVICENUMBER)))
            .andExpect(jsonPath("$.[*].companyid").value(hasItem(DEFAULT_COMPANYID)))
            .andExpect(jsonPath("$.[*].updatetocustomer").value(hasItem(DEFAULT_UPDATETOCUSTOMER)))
            .andExpect(jsonPath("$.[*].nextgearoilmilage").value(hasItem(DEFAULT_NEXTGEAROILMILAGE)))
            .andExpect(jsonPath("$.[*].isjobinvoiced").value(hasItem(DEFAULT_ISJOBINVOICED)))
            .andExpect(jsonPath("$.[*].iswaiting").value(hasItem(DEFAULT_ISWAITING)))
            .andExpect(jsonPath("$.[*].iscustomercomment").value(hasItem(DEFAULT_ISCUSTOMERCOMMENT)))
            .andExpect(jsonPath("$.[*].imagefolder").value(hasItem(DEFAULT_IMAGEFOLDER)))
            .andExpect(jsonPath("$.[*].frontimage").value(hasItem(DEFAULT_FRONTIMAGE)))
            .andExpect(jsonPath("$.[*].leftimage").value(hasItem(DEFAULT_LEFTIMAGE)))
            .andExpect(jsonPath("$.[*].rightimage").value(hasItem(DEFAULT_RIGHTIMAGE)))
            .andExpect(jsonPath("$.[*].backimage").value(hasItem(DEFAULT_BACKIMAGE)))
            .andExpect(jsonPath("$.[*].dashboardimage").value(hasItem(DEFAULT_DASHBOARDIMAGE)));
    }

    @Test
    @Transactional
    void getAutocarejob() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get the autocarejob
        restAutocarejobMockMvc
            .perform(get(ENTITY_API_URL_ID, autocarejob.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autocarejob.getId().intValue()))
            .andExpect(jsonPath("$.jobnumber").value(DEFAULT_JOBNUMBER))
            .andExpect(jsonPath("$.vehicleid").value(DEFAULT_VEHICLEID))
            .andExpect(jsonPath("$.vehiclenumber").value(DEFAULT_VEHICLENUMBER))
            .andExpect(jsonPath("$.millage").value(DEFAULT_MILLAGE.doubleValue()))
            .andExpect(jsonPath("$.nextmillage").value(DEFAULT_NEXTMILLAGE.doubleValue()))
            .andExpect(jsonPath("$.nextservicedate").value(DEFAULT_NEXTSERVICEDATE.toString()))
            .andExpect(jsonPath("$.vehicletypeid").value(DEFAULT_VEHICLETYPEID))
            .andExpect(jsonPath("$.jobtypeid").value(DEFAULT_JOBTYPEID))
            .andExpect(jsonPath("$.jobtypename").value(DEFAULT_JOBTYPENAME))
            .andExpect(jsonPath("$.jobopenby").value(DEFAULT_JOBOPENBY))
            .andExpect(jsonPath("$.jobopentime").value(DEFAULT_JOBOPENTIME.toString()))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.specialrquirments").value(DEFAULT_SPECIALRQUIRMENTS))
            .andExpect(jsonPath("$.specialinstructions").value(DEFAULT_SPECIALINSTRUCTIONS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.nextserviceinstructions").value(DEFAULT_NEXTSERVICEINSTRUCTIONS))
            .andExpect(jsonPath("$.lastserviceinstructions").value(DEFAULT_LASTSERVICEINSTRUCTIONS))
            .andExpect(jsonPath("$.isadvisorchecked").value(DEFAULT_ISADVISORCHECKED))
            .andExpect(jsonPath("$.isempallocated").value(DEFAULT_ISEMPALLOCATED))
            .andExpect(jsonPath("$.jobclosetime").value(DEFAULT_JOBCLOSETIME.toString()))
            .andExpect(jsonPath("$.isjobclose").value(DEFAULT_ISJOBCLOSE))
            .andExpect(jsonPath("$.isfeedback").value(DEFAULT_ISFEEDBACK))
            .andExpect(jsonPath("$.feedbackstatusid").value(DEFAULT_FEEDBACKSTATUSID))
            .andExpect(jsonPath("$.customername").value(DEFAULT_CUSTOMERNAME))
            .andExpect(jsonPath("$.customertel").value(DEFAULT_CUSTOMERTEL))
            .andExpect(jsonPath("$.customerid").value(DEFAULT_CUSTOMERID))
            .andExpect(jsonPath("$.advisorfinalcheck").value(DEFAULT_ADVISORFINALCHECK))
            .andExpect(jsonPath("$.jobdate").value(DEFAULT_JOBDATE.toString()))
            .andExpect(jsonPath("$.iscompanyservice").value(DEFAULT_ISCOMPANYSERVICE))
            .andExpect(jsonPath("$.freeservicenumber").value(DEFAULT_FREESERVICENUMBER))
            .andExpect(jsonPath("$.companyid").value(DEFAULT_COMPANYID))
            .andExpect(jsonPath("$.updatetocustomer").value(DEFAULT_UPDATETOCUSTOMER))
            .andExpect(jsonPath("$.nextgearoilmilage").value(DEFAULT_NEXTGEAROILMILAGE))
            .andExpect(jsonPath("$.isjobinvoiced").value(DEFAULT_ISJOBINVOICED))
            .andExpect(jsonPath("$.iswaiting").value(DEFAULT_ISWAITING))
            .andExpect(jsonPath("$.iscustomercomment").value(DEFAULT_ISCUSTOMERCOMMENT))
            .andExpect(jsonPath("$.imagefolder").value(DEFAULT_IMAGEFOLDER))
            .andExpect(jsonPath("$.frontimage").value(DEFAULT_FRONTIMAGE))
            .andExpect(jsonPath("$.leftimage").value(DEFAULT_LEFTIMAGE))
            .andExpect(jsonPath("$.rightimage").value(DEFAULT_RIGHTIMAGE))
            .andExpect(jsonPath("$.backimage").value(DEFAULT_BACKIMAGE))
            .andExpect(jsonPath("$.dashboardimage").value(DEFAULT_DASHBOARDIMAGE));
    }

    @Test
    @Transactional
    void getAutocarejobsByIdFiltering() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        Long id = autocarejob.getId();

        defaultAutocarejobFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAutocarejobFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAutocarejobFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobnumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobnumber equals to
        defaultAutocarejobFiltering("jobnumber.equals=" + DEFAULT_JOBNUMBER, "jobnumber.equals=" + UPDATED_JOBNUMBER);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobnumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobnumber in
        defaultAutocarejobFiltering("jobnumber.in=" + DEFAULT_JOBNUMBER + "," + UPDATED_JOBNUMBER, "jobnumber.in=" + UPDATED_JOBNUMBER);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobnumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobnumber is not null
        defaultAutocarejobFiltering("jobnumber.specified=true", "jobnumber.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobnumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobnumber is greater than or equal to
        defaultAutocarejobFiltering(
            "jobnumber.greaterThanOrEqual=" + DEFAULT_JOBNUMBER,
            "jobnumber.greaterThanOrEqual=" + UPDATED_JOBNUMBER
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobnumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobnumber is less than or equal to
        defaultAutocarejobFiltering("jobnumber.lessThanOrEqual=" + DEFAULT_JOBNUMBER, "jobnumber.lessThanOrEqual=" + SMALLER_JOBNUMBER);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobnumberIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobnumber is less than
        defaultAutocarejobFiltering("jobnumber.lessThan=" + UPDATED_JOBNUMBER, "jobnumber.lessThan=" + DEFAULT_JOBNUMBER);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobnumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobnumber is greater than
        defaultAutocarejobFiltering("jobnumber.greaterThan=" + SMALLER_JOBNUMBER, "jobnumber.greaterThan=" + DEFAULT_JOBNUMBER);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehicleidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehicleid equals to
        defaultAutocarejobFiltering("vehicleid.equals=" + DEFAULT_VEHICLEID, "vehicleid.equals=" + UPDATED_VEHICLEID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehicleidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehicleid in
        defaultAutocarejobFiltering("vehicleid.in=" + DEFAULT_VEHICLEID + "," + UPDATED_VEHICLEID, "vehicleid.in=" + UPDATED_VEHICLEID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehicleidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehicleid is not null
        defaultAutocarejobFiltering("vehicleid.specified=true", "vehicleid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehicleidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehicleid is greater than or equal to
        defaultAutocarejobFiltering(
            "vehicleid.greaterThanOrEqual=" + DEFAULT_VEHICLEID,
            "vehicleid.greaterThanOrEqual=" + UPDATED_VEHICLEID
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehicleidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehicleid is less than or equal to
        defaultAutocarejobFiltering("vehicleid.lessThanOrEqual=" + DEFAULT_VEHICLEID, "vehicleid.lessThanOrEqual=" + SMALLER_VEHICLEID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehicleidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehicleid is less than
        defaultAutocarejobFiltering("vehicleid.lessThan=" + UPDATED_VEHICLEID, "vehicleid.lessThan=" + DEFAULT_VEHICLEID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehicleidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehicleid is greater than
        defaultAutocarejobFiltering("vehicleid.greaterThan=" + SMALLER_VEHICLEID, "vehicleid.greaterThan=" + DEFAULT_VEHICLEID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehiclenumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehiclenumber equals to
        defaultAutocarejobFiltering("vehiclenumber.equals=" + DEFAULT_VEHICLENUMBER, "vehiclenumber.equals=" + UPDATED_VEHICLENUMBER);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehiclenumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehiclenumber in
        defaultAutocarejobFiltering(
            "vehiclenumber.in=" + DEFAULT_VEHICLENUMBER + "," + UPDATED_VEHICLENUMBER,
            "vehiclenumber.in=" + UPDATED_VEHICLENUMBER
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehiclenumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehiclenumber is not null
        defaultAutocarejobFiltering("vehiclenumber.specified=true", "vehiclenumber.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehiclenumberContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehiclenumber contains
        defaultAutocarejobFiltering("vehiclenumber.contains=" + DEFAULT_VEHICLENUMBER, "vehiclenumber.contains=" + UPDATED_VEHICLENUMBER);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehiclenumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehiclenumber does not contain
        defaultAutocarejobFiltering(
            "vehiclenumber.doesNotContain=" + UPDATED_VEHICLENUMBER,
            "vehiclenumber.doesNotContain=" + DEFAULT_VEHICLENUMBER
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByMillageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where millage equals to
        defaultAutocarejobFiltering("millage.equals=" + DEFAULT_MILLAGE, "millage.equals=" + UPDATED_MILLAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByMillageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where millage in
        defaultAutocarejobFiltering("millage.in=" + DEFAULT_MILLAGE + "," + UPDATED_MILLAGE, "millage.in=" + UPDATED_MILLAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByMillageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where millage is not null
        defaultAutocarejobFiltering("millage.specified=true", "millage.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByMillageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where millage is greater than or equal to
        defaultAutocarejobFiltering("millage.greaterThanOrEqual=" + DEFAULT_MILLAGE, "millage.greaterThanOrEqual=" + UPDATED_MILLAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByMillageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where millage is less than or equal to
        defaultAutocarejobFiltering("millage.lessThanOrEqual=" + DEFAULT_MILLAGE, "millage.lessThanOrEqual=" + SMALLER_MILLAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByMillageIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where millage is less than
        defaultAutocarejobFiltering("millage.lessThan=" + UPDATED_MILLAGE, "millage.lessThan=" + DEFAULT_MILLAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByMillageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where millage is greater than
        defaultAutocarejobFiltering("millage.greaterThan=" + SMALLER_MILLAGE, "millage.greaterThan=" + DEFAULT_MILLAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextmillageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextmillage equals to
        defaultAutocarejobFiltering("nextmillage.equals=" + DEFAULT_NEXTMILLAGE, "nextmillage.equals=" + UPDATED_NEXTMILLAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextmillageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextmillage in
        defaultAutocarejobFiltering(
            "nextmillage.in=" + DEFAULT_NEXTMILLAGE + "," + UPDATED_NEXTMILLAGE,
            "nextmillage.in=" + UPDATED_NEXTMILLAGE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextmillageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextmillage is not null
        defaultAutocarejobFiltering("nextmillage.specified=true", "nextmillage.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextmillageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextmillage is greater than or equal to
        defaultAutocarejobFiltering(
            "nextmillage.greaterThanOrEqual=" + DEFAULT_NEXTMILLAGE,
            "nextmillage.greaterThanOrEqual=" + UPDATED_NEXTMILLAGE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextmillageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextmillage is less than or equal to
        defaultAutocarejobFiltering(
            "nextmillage.lessThanOrEqual=" + DEFAULT_NEXTMILLAGE,
            "nextmillage.lessThanOrEqual=" + SMALLER_NEXTMILLAGE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextmillageIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextmillage is less than
        defaultAutocarejobFiltering("nextmillage.lessThan=" + UPDATED_NEXTMILLAGE, "nextmillage.lessThan=" + DEFAULT_NEXTMILLAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextmillageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextmillage is greater than
        defaultAutocarejobFiltering("nextmillage.greaterThan=" + SMALLER_NEXTMILLAGE, "nextmillage.greaterThan=" + DEFAULT_NEXTMILLAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextservicedateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextservicedate equals to
        defaultAutocarejobFiltering(
            "nextservicedate.equals=" + DEFAULT_NEXTSERVICEDATE,
            "nextservicedate.equals=" + UPDATED_NEXTSERVICEDATE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextservicedateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextservicedate in
        defaultAutocarejobFiltering(
            "nextservicedate.in=" + DEFAULT_NEXTSERVICEDATE + "," + UPDATED_NEXTSERVICEDATE,
            "nextservicedate.in=" + UPDATED_NEXTSERVICEDATE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextservicedateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextservicedate is not null
        defaultAutocarejobFiltering("nextservicedate.specified=true", "nextservicedate.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextservicedateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextservicedate is greater than or equal to
        defaultAutocarejobFiltering(
            "nextservicedate.greaterThanOrEqual=" + DEFAULT_NEXTSERVICEDATE,
            "nextservicedate.greaterThanOrEqual=" + UPDATED_NEXTSERVICEDATE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextservicedateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextservicedate is less than or equal to
        defaultAutocarejobFiltering(
            "nextservicedate.lessThanOrEqual=" + DEFAULT_NEXTSERVICEDATE,
            "nextservicedate.lessThanOrEqual=" + SMALLER_NEXTSERVICEDATE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextservicedateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextservicedate is less than
        defaultAutocarejobFiltering(
            "nextservicedate.lessThan=" + UPDATED_NEXTSERVICEDATE,
            "nextservicedate.lessThan=" + DEFAULT_NEXTSERVICEDATE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextservicedateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextservicedate is greater than
        defaultAutocarejobFiltering(
            "nextservicedate.greaterThan=" + SMALLER_NEXTSERVICEDATE,
            "nextservicedate.greaterThan=" + DEFAULT_NEXTSERVICEDATE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehicletypeidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehicletypeid equals to
        defaultAutocarejobFiltering("vehicletypeid.equals=" + DEFAULT_VEHICLETYPEID, "vehicletypeid.equals=" + UPDATED_VEHICLETYPEID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehicletypeidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehicletypeid in
        defaultAutocarejobFiltering(
            "vehicletypeid.in=" + DEFAULT_VEHICLETYPEID + "," + UPDATED_VEHICLETYPEID,
            "vehicletypeid.in=" + UPDATED_VEHICLETYPEID
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehicletypeidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehicletypeid is not null
        defaultAutocarejobFiltering("vehicletypeid.specified=true", "vehicletypeid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehicletypeidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehicletypeid is greater than or equal to
        defaultAutocarejobFiltering(
            "vehicletypeid.greaterThanOrEqual=" + DEFAULT_VEHICLETYPEID,
            "vehicletypeid.greaterThanOrEqual=" + UPDATED_VEHICLETYPEID
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehicletypeidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehicletypeid is less than or equal to
        defaultAutocarejobFiltering(
            "vehicletypeid.lessThanOrEqual=" + DEFAULT_VEHICLETYPEID,
            "vehicletypeid.lessThanOrEqual=" + SMALLER_VEHICLETYPEID
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehicletypeidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehicletypeid is less than
        defaultAutocarejobFiltering("vehicletypeid.lessThan=" + UPDATED_VEHICLETYPEID, "vehicletypeid.lessThan=" + DEFAULT_VEHICLETYPEID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByVehicletypeidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where vehicletypeid is greater than
        defaultAutocarejobFiltering(
            "vehicletypeid.greaterThan=" + SMALLER_VEHICLETYPEID,
            "vehicletypeid.greaterThan=" + DEFAULT_VEHICLETYPEID
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobtypeidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobtypeid equals to
        defaultAutocarejobFiltering("jobtypeid.equals=" + DEFAULT_JOBTYPEID, "jobtypeid.equals=" + UPDATED_JOBTYPEID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobtypeidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobtypeid in
        defaultAutocarejobFiltering("jobtypeid.in=" + DEFAULT_JOBTYPEID + "," + UPDATED_JOBTYPEID, "jobtypeid.in=" + UPDATED_JOBTYPEID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobtypeidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobtypeid is not null
        defaultAutocarejobFiltering("jobtypeid.specified=true", "jobtypeid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobtypeidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobtypeid is greater than or equal to
        defaultAutocarejobFiltering(
            "jobtypeid.greaterThanOrEqual=" + DEFAULT_JOBTYPEID,
            "jobtypeid.greaterThanOrEqual=" + UPDATED_JOBTYPEID
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobtypeidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobtypeid is less than or equal to
        defaultAutocarejobFiltering("jobtypeid.lessThanOrEqual=" + DEFAULT_JOBTYPEID, "jobtypeid.lessThanOrEqual=" + SMALLER_JOBTYPEID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobtypeidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobtypeid is less than
        defaultAutocarejobFiltering("jobtypeid.lessThan=" + UPDATED_JOBTYPEID, "jobtypeid.lessThan=" + DEFAULT_JOBTYPEID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobtypeidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobtypeid is greater than
        defaultAutocarejobFiltering("jobtypeid.greaterThan=" + SMALLER_JOBTYPEID, "jobtypeid.greaterThan=" + DEFAULT_JOBTYPEID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobtypenameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobtypename equals to
        defaultAutocarejobFiltering("jobtypename.equals=" + DEFAULT_JOBTYPENAME, "jobtypename.equals=" + UPDATED_JOBTYPENAME);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobtypenameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobtypename in
        defaultAutocarejobFiltering(
            "jobtypename.in=" + DEFAULT_JOBTYPENAME + "," + UPDATED_JOBTYPENAME,
            "jobtypename.in=" + UPDATED_JOBTYPENAME
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobtypenameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobtypename is not null
        defaultAutocarejobFiltering("jobtypename.specified=true", "jobtypename.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobtypenameContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobtypename contains
        defaultAutocarejobFiltering("jobtypename.contains=" + DEFAULT_JOBTYPENAME, "jobtypename.contains=" + UPDATED_JOBTYPENAME);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobtypenameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobtypename does not contain
        defaultAutocarejobFiltering(
            "jobtypename.doesNotContain=" + UPDATED_JOBTYPENAME,
            "jobtypename.doesNotContain=" + DEFAULT_JOBTYPENAME
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobopenbyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobopenby equals to
        defaultAutocarejobFiltering("jobopenby.equals=" + DEFAULT_JOBOPENBY, "jobopenby.equals=" + UPDATED_JOBOPENBY);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobopenbyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobopenby in
        defaultAutocarejobFiltering("jobopenby.in=" + DEFAULT_JOBOPENBY + "," + UPDATED_JOBOPENBY, "jobopenby.in=" + UPDATED_JOBOPENBY);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobopenbyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobopenby is not null
        defaultAutocarejobFiltering("jobopenby.specified=true", "jobopenby.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobopenbyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobopenby is greater than or equal to
        defaultAutocarejobFiltering(
            "jobopenby.greaterThanOrEqual=" + DEFAULT_JOBOPENBY,
            "jobopenby.greaterThanOrEqual=" + UPDATED_JOBOPENBY
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobopenbyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobopenby is less than or equal to
        defaultAutocarejobFiltering("jobopenby.lessThanOrEqual=" + DEFAULT_JOBOPENBY, "jobopenby.lessThanOrEqual=" + SMALLER_JOBOPENBY);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobopenbyIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobopenby is less than
        defaultAutocarejobFiltering("jobopenby.lessThan=" + UPDATED_JOBOPENBY, "jobopenby.lessThan=" + DEFAULT_JOBOPENBY);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobopenbyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobopenby is greater than
        defaultAutocarejobFiltering("jobopenby.greaterThan=" + SMALLER_JOBOPENBY, "jobopenby.greaterThan=" + DEFAULT_JOBOPENBY);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobopentimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobopentime equals to
        defaultAutocarejobFiltering("jobopentime.equals=" + DEFAULT_JOBOPENTIME, "jobopentime.equals=" + UPDATED_JOBOPENTIME);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobopentimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobopentime in
        defaultAutocarejobFiltering(
            "jobopentime.in=" + DEFAULT_JOBOPENTIME + "," + UPDATED_JOBOPENTIME,
            "jobopentime.in=" + UPDATED_JOBOPENTIME
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobopentimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobopentime is not null
        defaultAutocarejobFiltering("jobopentime.specified=true", "jobopentime.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where lmu equals to
        defaultAutocarejobFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where lmu in
        defaultAutocarejobFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where lmu is not null
        defaultAutocarejobFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where lmu is greater than or equal to
        defaultAutocarejobFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where lmu is less than or equal to
        defaultAutocarejobFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where lmu is less than
        defaultAutocarejobFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where lmu is greater than
        defaultAutocarejobFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where lmd equals to
        defaultAutocarejobFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where lmd in
        defaultAutocarejobFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where lmd is not null
        defaultAutocarejobFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsBySpecialrquirmentsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where specialrquirments equals to
        defaultAutocarejobFiltering(
            "specialrquirments.equals=" + DEFAULT_SPECIALRQUIRMENTS,
            "specialrquirments.equals=" + UPDATED_SPECIALRQUIRMENTS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsBySpecialrquirmentsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where specialrquirments in
        defaultAutocarejobFiltering(
            "specialrquirments.in=" + DEFAULT_SPECIALRQUIRMENTS + "," + UPDATED_SPECIALRQUIRMENTS,
            "specialrquirments.in=" + UPDATED_SPECIALRQUIRMENTS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsBySpecialrquirmentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where specialrquirments is not null
        defaultAutocarejobFiltering("specialrquirments.specified=true", "specialrquirments.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsBySpecialrquirmentsContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where specialrquirments contains
        defaultAutocarejobFiltering(
            "specialrquirments.contains=" + DEFAULT_SPECIALRQUIRMENTS,
            "specialrquirments.contains=" + UPDATED_SPECIALRQUIRMENTS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsBySpecialrquirmentsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where specialrquirments does not contain
        defaultAutocarejobFiltering(
            "specialrquirments.doesNotContain=" + UPDATED_SPECIALRQUIRMENTS,
            "specialrquirments.doesNotContain=" + DEFAULT_SPECIALRQUIRMENTS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsBySpecialinstructionsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where specialinstructions equals to
        defaultAutocarejobFiltering(
            "specialinstructions.equals=" + DEFAULT_SPECIALINSTRUCTIONS,
            "specialinstructions.equals=" + UPDATED_SPECIALINSTRUCTIONS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsBySpecialinstructionsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where specialinstructions in
        defaultAutocarejobFiltering(
            "specialinstructions.in=" + DEFAULT_SPECIALINSTRUCTIONS + "," + UPDATED_SPECIALINSTRUCTIONS,
            "specialinstructions.in=" + UPDATED_SPECIALINSTRUCTIONS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsBySpecialinstructionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where specialinstructions is not null
        defaultAutocarejobFiltering("specialinstructions.specified=true", "specialinstructions.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsBySpecialinstructionsContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where specialinstructions contains
        defaultAutocarejobFiltering(
            "specialinstructions.contains=" + DEFAULT_SPECIALINSTRUCTIONS,
            "specialinstructions.contains=" + UPDATED_SPECIALINSTRUCTIONS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsBySpecialinstructionsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where specialinstructions does not contain
        defaultAutocarejobFiltering(
            "specialinstructions.doesNotContain=" + UPDATED_SPECIALINSTRUCTIONS,
            "specialinstructions.doesNotContain=" + DEFAULT_SPECIALINSTRUCTIONS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where remarks equals to
        defaultAutocarejobFiltering("remarks.equals=" + DEFAULT_REMARKS, "remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where remarks in
        defaultAutocarejobFiltering("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS, "remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where remarks is not null
        defaultAutocarejobFiltering("remarks.specified=true", "remarks.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByRemarksContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where remarks contains
        defaultAutocarejobFiltering("remarks.contains=" + DEFAULT_REMARKS, "remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where remarks does not contain
        defaultAutocarejobFiltering("remarks.doesNotContain=" + UPDATED_REMARKS, "remarks.doesNotContain=" + DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextserviceinstructionsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextserviceinstructions equals to
        defaultAutocarejobFiltering(
            "nextserviceinstructions.equals=" + DEFAULT_NEXTSERVICEINSTRUCTIONS,
            "nextserviceinstructions.equals=" + UPDATED_NEXTSERVICEINSTRUCTIONS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextserviceinstructionsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextserviceinstructions in
        defaultAutocarejobFiltering(
            "nextserviceinstructions.in=" + DEFAULT_NEXTSERVICEINSTRUCTIONS + "," + UPDATED_NEXTSERVICEINSTRUCTIONS,
            "nextserviceinstructions.in=" + UPDATED_NEXTSERVICEINSTRUCTIONS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextserviceinstructionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextserviceinstructions is not null
        defaultAutocarejobFiltering("nextserviceinstructions.specified=true", "nextserviceinstructions.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextserviceinstructionsContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextserviceinstructions contains
        defaultAutocarejobFiltering(
            "nextserviceinstructions.contains=" + DEFAULT_NEXTSERVICEINSTRUCTIONS,
            "nextserviceinstructions.contains=" + UPDATED_NEXTSERVICEINSTRUCTIONS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextserviceinstructionsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextserviceinstructions does not contain
        defaultAutocarejobFiltering(
            "nextserviceinstructions.doesNotContain=" + UPDATED_NEXTSERVICEINSTRUCTIONS,
            "nextserviceinstructions.doesNotContain=" + DEFAULT_NEXTSERVICEINSTRUCTIONS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLastserviceinstructionsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where lastserviceinstructions equals to
        defaultAutocarejobFiltering(
            "lastserviceinstructions.equals=" + DEFAULT_LASTSERVICEINSTRUCTIONS,
            "lastserviceinstructions.equals=" + UPDATED_LASTSERVICEINSTRUCTIONS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLastserviceinstructionsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where lastserviceinstructions in
        defaultAutocarejobFiltering(
            "lastserviceinstructions.in=" + DEFAULT_LASTSERVICEINSTRUCTIONS + "," + UPDATED_LASTSERVICEINSTRUCTIONS,
            "lastserviceinstructions.in=" + UPDATED_LASTSERVICEINSTRUCTIONS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLastserviceinstructionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where lastserviceinstructions is not null
        defaultAutocarejobFiltering("lastserviceinstructions.specified=true", "lastserviceinstructions.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLastserviceinstructionsContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where lastserviceinstructions contains
        defaultAutocarejobFiltering(
            "lastserviceinstructions.contains=" + DEFAULT_LASTSERVICEINSTRUCTIONS,
            "lastserviceinstructions.contains=" + UPDATED_LASTSERVICEINSTRUCTIONS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLastserviceinstructionsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where lastserviceinstructions does not contain
        defaultAutocarejobFiltering(
            "lastserviceinstructions.doesNotContain=" + UPDATED_LASTSERVICEINSTRUCTIONS,
            "lastserviceinstructions.doesNotContain=" + DEFAULT_LASTSERVICEINSTRUCTIONS
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIsadvisorcheckedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where isadvisorchecked equals to
        defaultAutocarejobFiltering(
            "isadvisorchecked.equals=" + DEFAULT_ISADVISORCHECKED,
            "isadvisorchecked.equals=" + UPDATED_ISADVISORCHECKED
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIsadvisorcheckedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where isadvisorchecked in
        defaultAutocarejobFiltering(
            "isadvisorchecked.in=" + DEFAULT_ISADVISORCHECKED + "," + UPDATED_ISADVISORCHECKED,
            "isadvisorchecked.in=" + UPDATED_ISADVISORCHECKED
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIsadvisorcheckedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where isadvisorchecked is not null
        defaultAutocarejobFiltering("isadvisorchecked.specified=true", "isadvisorchecked.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIsempallocatedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where isempallocated equals to
        defaultAutocarejobFiltering("isempallocated.equals=" + DEFAULT_ISEMPALLOCATED, "isempallocated.equals=" + UPDATED_ISEMPALLOCATED);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIsempallocatedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where isempallocated in
        defaultAutocarejobFiltering(
            "isempallocated.in=" + DEFAULT_ISEMPALLOCATED + "," + UPDATED_ISEMPALLOCATED,
            "isempallocated.in=" + UPDATED_ISEMPALLOCATED
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIsempallocatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where isempallocated is not null
        defaultAutocarejobFiltering("isempallocated.specified=true", "isempallocated.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobclosetimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobclosetime equals to
        defaultAutocarejobFiltering("jobclosetime.equals=" + DEFAULT_JOBCLOSETIME, "jobclosetime.equals=" + UPDATED_JOBCLOSETIME);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobclosetimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobclosetime in
        defaultAutocarejobFiltering(
            "jobclosetime.in=" + DEFAULT_JOBCLOSETIME + "," + UPDATED_JOBCLOSETIME,
            "jobclosetime.in=" + UPDATED_JOBCLOSETIME
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobclosetimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobclosetime is not null
        defaultAutocarejobFiltering("jobclosetime.specified=true", "jobclosetime.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIsjobcloseIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where isjobclose equals to
        defaultAutocarejobFiltering("isjobclose.equals=" + DEFAULT_ISJOBCLOSE, "isjobclose.equals=" + UPDATED_ISJOBCLOSE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIsjobcloseIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where isjobclose in
        defaultAutocarejobFiltering(
            "isjobclose.in=" + DEFAULT_ISJOBCLOSE + "," + UPDATED_ISJOBCLOSE,
            "isjobclose.in=" + UPDATED_ISJOBCLOSE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIsjobcloseIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where isjobclose is not null
        defaultAutocarejobFiltering("isjobclose.specified=true", "isjobclose.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIsfeedbackIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where isfeedback equals to
        defaultAutocarejobFiltering("isfeedback.equals=" + DEFAULT_ISFEEDBACK, "isfeedback.equals=" + UPDATED_ISFEEDBACK);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIsfeedbackIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where isfeedback in
        defaultAutocarejobFiltering(
            "isfeedback.in=" + DEFAULT_ISFEEDBACK + "," + UPDATED_ISFEEDBACK,
            "isfeedback.in=" + UPDATED_ISFEEDBACK
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIsfeedbackIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where isfeedback is not null
        defaultAutocarejobFiltering("isfeedback.specified=true", "isfeedback.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFeedbackstatusidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where feedbackstatusid equals to
        defaultAutocarejobFiltering(
            "feedbackstatusid.equals=" + DEFAULT_FEEDBACKSTATUSID,
            "feedbackstatusid.equals=" + UPDATED_FEEDBACKSTATUSID
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFeedbackstatusidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where feedbackstatusid in
        defaultAutocarejobFiltering(
            "feedbackstatusid.in=" + DEFAULT_FEEDBACKSTATUSID + "," + UPDATED_FEEDBACKSTATUSID,
            "feedbackstatusid.in=" + UPDATED_FEEDBACKSTATUSID
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFeedbackstatusidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where feedbackstatusid is not null
        defaultAutocarejobFiltering("feedbackstatusid.specified=true", "feedbackstatusid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFeedbackstatusidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where feedbackstatusid is greater than or equal to
        defaultAutocarejobFiltering(
            "feedbackstatusid.greaterThanOrEqual=" + DEFAULT_FEEDBACKSTATUSID,
            "feedbackstatusid.greaterThanOrEqual=" + UPDATED_FEEDBACKSTATUSID
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFeedbackstatusidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where feedbackstatusid is less than or equal to
        defaultAutocarejobFiltering(
            "feedbackstatusid.lessThanOrEqual=" + DEFAULT_FEEDBACKSTATUSID,
            "feedbackstatusid.lessThanOrEqual=" + SMALLER_FEEDBACKSTATUSID
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFeedbackstatusidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where feedbackstatusid is less than
        defaultAutocarejobFiltering(
            "feedbackstatusid.lessThan=" + UPDATED_FEEDBACKSTATUSID,
            "feedbackstatusid.lessThan=" + DEFAULT_FEEDBACKSTATUSID
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFeedbackstatusidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where feedbackstatusid is greater than
        defaultAutocarejobFiltering(
            "feedbackstatusid.greaterThan=" + SMALLER_FEEDBACKSTATUSID,
            "feedbackstatusid.greaterThan=" + DEFAULT_FEEDBACKSTATUSID
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomernameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customername equals to
        defaultAutocarejobFiltering("customername.equals=" + DEFAULT_CUSTOMERNAME, "customername.equals=" + UPDATED_CUSTOMERNAME);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomernameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customername in
        defaultAutocarejobFiltering(
            "customername.in=" + DEFAULT_CUSTOMERNAME + "," + UPDATED_CUSTOMERNAME,
            "customername.in=" + UPDATED_CUSTOMERNAME
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customername is not null
        defaultAutocarejobFiltering("customername.specified=true", "customername.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomernameContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customername contains
        defaultAutocarejobFiltering("customername.contains=" + DEFAULT_CUSTOMERNAME, "customername.contains=" + UPDATED_CUSTOMERNAME);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomernameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customername does not contain
        defaultAutocarejobFiltering(
            "customername.doesNotContain=" + UPDATED_CUSTOMERNAME,
            "customername.doesNotContain=" + DEFAULT_CUSTOMERNAME
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomertelIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customertel equals to
        defaultAutocarejobFiltering("customertel.equals=" + DEFAULT_CUSTOMERTEL, "customertel.equals=" + UPDATED_CUSTOMERTEL);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomertelIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customertel in
        defaultAutocarejobFiltering(
            "customertel.in=" + DEFAULT_CUSTOMERTEL + "," + UPDATED_CUSTOMERTEL,
            "customertel.in=" + UPDATED_CUSTOMERTEL
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomertelIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customertel is not null
        defaultAutocarejobFiltering("customertel.specified=true", "customertel.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomertelContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customertel contains
        defaultAutocarejobFiltering("customertel.contains=" + DEFAULT_CUSTOMERTEL, "customertel.contains=" + UPDATED_CUSTOMERTEL);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomertelNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customertel does not contain
        defaultAutocarejobFiltering(
            "customertel.doesNotContain=" + UPDATED_CUSTOMERTEL,
            "customertel.doesNotContain=" + DEFAULT_CUSTOMERTEL
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomeridIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customerid equals to
        defaultAutocarejobFiltering("customerid.equals=" + DEFAULT_CUSTOMERID, "customerid.equals=" + UPDATED_CUSTOMERID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomeridIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customerid in
        defaultAutocarejobFiltering(
            "customerid.in=" + DEFAULT_CUSTOMERID + "," + UPDATED_CUSTOMERID,
            "customerid.in=" + UPDATED_CUSTOMERID
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomeridIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customerid is not null
        defaultAutocarejobFiltering("customerid.specified=true", "customerid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomeridIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customerid is greater than or equal to
        defaultAutocarejobFiltering(
            "customerid.greaterThanOrEqual=" + DEFAULT_CUSTOMERID,
            "customerid.greaterThanOrEqual=" + UPDATED_CUSTOMERID
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomeridIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customerid is less than or equal to
        defaultAutocarejobFiltering("customerid.lessThanOrEqual=" + DEFAULT_CUSTOMERID, "customerid.lessThanOrEqual=" + SMALLER_CUSTOMERID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomeridIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customerid is less than
        defaultAutocarejobFiltering("customerid.lessThan=" + UPDATED_CUSTOMERID, "customerid.lessThan=" + DEFAULT_CUSTOMERID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCustomeridIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where customerid is greater than
        defaultAutocarejobFiltering("customerid.greaterThan=" + SMALLER_CUSTOMERID, "customerid.greaterThan=" + DEFAULT_CUSTOMERID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByAdvisorfinalcheckIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where advisorfinalcheck equals to
        defaultAutocarejobFiltering(
            "advisorfinalcheck.equals=" + DEFAULT_ADVISORFINALCHECK,
            "advisorfinalcheck.equals=" + UPDATED_ADVISORFINALCHECK
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByAdvisorfinalcheckIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where advisorfinalcheck in
        defaultAutocarejobFiltering(
            "advisorfinalcheck.in=" + DEFAULT_ADVISORFINALCHECK + "," + UPDATED_ADVISORFINALCHECK,
            "advisorfinalcheck.in=" + UPDATED_ADVISORFINALCHECK
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByAdvisorfinalcheckIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where advisorfinalcheck is not null
        defaultAutocarejobFiltering("advisorfinalcheck.specified=true", "advisorfinalcheck.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobdateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobdate equals to
        defaultAutocarejobFiltering("jobdate.equals=" + DEFAULT_JOBDATE, "jobdate.equals=" + UPDATED_JOBDATE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobdateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobdate in
        defaultAutocarejobFiltering("jobdate.in=" + DEFAULT_JOBDATE + "," + UPDATED_JOBDATE, "jobdate.in=" + UPDATED_JOBDATE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByJobdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where jobdate is not null
        defaultAutocarejobFiltering("jobdate.specified=true", "jobdate.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIscompanyserviceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where iscompanyservice equals to
        defaultAutocarejobFiltering(
            "iscompanyservice.equals=" + DEFAULT_ISCOMPANYSERVICE,
            "iscompanyservice.equals=" + UPDATED_ISCOMPANYSERVICE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIscompanyserviceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where iscompanyservice in
        defaultAutocarejobFiltering(
            "iscompanyservice.in=" + DEFAULT_ISCOMPANYSERVICE + "," + UPDATED_ISCOMPANYSERVICE,
            "iscompanyservice.in=" + UPDATED_ISCOMPANYSERVICE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIscompanyserviceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where iscompanyservice is not null
        defaultAutocarejobFiltering("iscompanyservice.specified=true", "iscompanyservice.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFreeservicenumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where freeservicenumber equals to
        defaultAutocarejobFiltering(
            "freeservicenumber.equals=" + DEFAULT_FREESERVICENUMBER,
            "freeservicenumber.equals=" + UPDATED_FREESERVICENUMBER
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFreeservicenumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where freeservicenumber in
        defaultAutocarejobFiltering(
            "freeservicenumber.in=" + DEFAULT_FREESERVICENUMBER + "," + UPDATED_FREESERVICENUMBER,
            "freeservicenumber.in=" + UPDATED_FREESERVICENUMBER
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFreeservicenumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where freeservicenumber is not null
        defaultAutocarejobFiltering("freeservicenumber.specified=true", "freeservicenumber.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFreeservicenumberContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where freeservicenumber contains
        defaultAutocarejobFiltering(
            "freeservicenumber.contains=" + DEFAULT_FREESERVICENUMBER,
            "freeservicenumber.contains=" + UPDATED_FREESERVICENUMBER
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFreeservicenumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where freeservicenumber does not contain
        defaultAutocarejobFiltering(
            "freeservicenumber.doesNotContain=" + UPDATED_FREESERVICENUMBER,
            "freeservicenumber.doesNotContain=" + DEFAULT_FREESERVICENUMBER
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCompanyidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where companyid equals to
        defaultAutocarejobFiltering("companyid.equals=" + DEFAULT_COMPANYID, "companyid.equals=" + UPDATED_COMPANYID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCompanyidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where companyid in
        defaultAutocarejobFiltering("companyid.in=" + DEFAULT_COMPANYID + "," + UPDATED_COMPANYID, "companyid.in=" + UPDATED_COMPANYID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCompanyidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where companyid is not null
        defaultAutocarejobFiltering("companyid.specified=true", "companyid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCompanyidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where companyid is greater than or equal to
        defaultAutocarejobFiltering(
            "companyid.greaterThanOrEqual=" + DEFAULT_COMPANYID,
            "companyid.greaterThanOrEqual=" + UPDATED_COMPANYID
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCompanyidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where companyid is less than or equal to
        defaultAutocarejobFiltering("companyid.lessThanOrEqual=" + DEFAULT_COMPANYID, "companyid.lessThanOrEqual=" + SMALLER_COMPANYID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCompanyidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where companyid is less than
        defaultAutocarejobFiltering("companyid.lessThan=" + UPDATED_COMPANYID, "companyid.lessThan=" + DEFAULT_COMPANYID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByCompanyidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where companyid is greater than
        defaultAutocarejobFiltering("companyid.greaterThan=" + SMALLER_COMPANYID, "companyid.greaterThan=" + DEFAULT_COMPANYID);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByUpdatetocustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where updatetocustomer equals to
        defaultAutocarejobFiltering(
            "updatetocustomer.equals=" + DEFAULT_UPDATETOCUSTOMER,
            "updatetocustomer.equals=" + UPDATED_UPDATETOCUSTOMER
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByUpdatetocustomerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where updatetocustomer in
        defaultAutocarejobFiltering(
            "updatetocustomer.in=" + DEFAULT_UPDATETOCUSTOMER + "," + UPDATED_UPDATETOCUSTOMER,
            "updatetocustomer.in=" + UPDATED_UPDATETOCUSTOMER
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByUpdatetocustomerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where updatetocustomer is not null
        defaultAutocarejobFiltering("updatetocustomer.specified=true", "updatetocustomer.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextgearoilmilageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextgearoilmilage equals to
        defaultAutocarejobFiltering(
            "nextgearoilmilage.equals=" + DEFAULT_NEXTGEAROILMILAGE,
            "nextgearoilmilage.equals=" + UPDATED_NEXTGEAROILMILAGE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextgearoilmilageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextgearoilmilage in
        defaultAutocarejobFiltering(
            "nextgearoilmilage.in=" + DEFAULT_NEXTGEAROILMILAGE + "," + UPDATED_NEXTGEAROILMILAGE,
            "nextgearoilmilage.in=" + UPDATED_NEXTGEAROILMILAGE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextgearoilmilageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextgearoilmilage is not null
        defaultAutocarejobFiltering("nextgearoilmilage.specified=true", "nextgearoilmilage.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextgearoilmilageContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextgearoilmilage contains
        defaultAutocarejobFiltering(
            "nextgearoilmilage.contains=" + DEFAULT_NEXTGEAROILMILAGE,
            "nextgearoilmilage.contains=" + UPDATED_NEXTGEAROILMILAGE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByNextgearoilmilageNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where nextgearoilmilage does not contain
        defaultAutocarejobFiltering(
            "nextgearoilmilage.doesNotContain=" + UPDATED_NEXTGEAROILMILAGE,
            "nextgearoilmilage.doesNotContain=" + DEFAULT_NEXTGEAROILMILAGE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIsjobinvoicedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where isjobinvoiced equals to
        defaultAutocarejobFiltering("isjobinvoiced.equals=" + DEFAULT_ISJOBINVOICED, "isjobinvoiced.equals=" + UPDATED_ISJOBINVOICED);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIsjobinvoicedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where isjobinvoiced in
        defaultAutocarejobFiltering(
            "isjobinvoiced.in=" + DEFAULT_ISJOBINVOICED + "," + UPDATED_ISJOBINVOICED,
            "isjobinvoiced.in=" + UPDATED_ISJOBINVOICED
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIsjobinvoicedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where isjobinvoiced is not null
        defaultAutocarejobFiltering("isjobinvoiced.specified=true", "isjobinvoiced.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIswaitingIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where iswaiting equals to
        defaultAutocarejobFiltering("iswaiting.equals=" + DEFAULT_ISWAITING, "iswaiting.equals=" + UPDATED_ISWAITING);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIswaitingIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where iswaiting in
        defaultAutocarejobFiltering("iswaiting.in=" + DEFAULT_ISWAITING + "," + UPDATED_ISWAITING, "iswaiting.in=" + UPDATED_ISWAITING);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIswaitingIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where iswaiting is not null
        defaultAutocarejobFiltering("iswaiting.specified=true", "iswaiting.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIscustomercommentIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where iscustomercomment equals to
        defaultAutocarejobFiltering(
            "iscustomercomment.equals=" + DEFAULT_ISCUSTOMERCOMMENT,
            "iscustomercomment.equals=" + UPDATED_ISCUSTOMERCOMMENT
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIscustomercommentIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where iscustomercomment in
        defaultAutocarejobFiltering(
            "iscustomercomment.in=" + DEFAULT_ISCUSTOMERCOMMENT + "," + UPDATED_ISCUSTOMERCOMMENT,
            "iscustomercomment.in=" + UPDATED_ISCUSTOMERCOMMENT
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByIscustomercommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where iscustomercomment is not null
        defaultAutocarejobFiltering("iscustomercomment.specified=true", "iscustomercomment.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByImagefolderIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where imagefolder equals to
        defaultAutocarejobFiltering("imagefolder.equals=" + DEFAULT_IMAGEFOLDER, "imagefolder.equals=" + UPDATED_IMAGEFOLDER);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByImagefolderIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where imagefolder in
        defaultAutocarejobFiltering(
            "imagefolder.in=" + DEFAULT_IMAGEFOLDER + "," + UPDATED_IMAGEFOLDER,
            "imagefolder.in=" + UPDATED_IMAGEFOLDER
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByImagefolderIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where imagefolder is not null
        defaultAutocarejobFiltering("imagefolder.specified=true", "imagefolder.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByImagefolderContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where imagefolder contains
        defaultAutocarejobFiltering("imagefolder.contains=" + DEFAULT_IMAGEFOLDER, "imagefolder.contains=" + UPDATED_IMAGEFOLDER);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByImagefolderNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where imagefolder does not contain
        defaultAutocarejobFiltering(
            "imagefolder.doesNotContain=" + UPDATED_IMAGEFOLDER,
            "imagefolder.doesNotContain=" + DEFAULT_IMAGEFOLDER
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFrontimageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where frontimage equals to
        defaultAutocarejobFiltering("frontimage.equals=" + DEFAULT_FRONTIMAGE, "frontimage.equals=" + UPDATED_FRONTIMAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFrontimageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where frontimage in
        defaultAutocarejobFiltering(
            "frontimage.in=" + DEFAULT_FRONTIMAGE + "," + UPDATED_FRONTIMAGE,
            "frontimage.in=" + UPDATED_FRONTIMAGE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFrontimageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where frontimage is not null
        defaultAutocarejobFiltering("frontimage.specified=true", "frontimage.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFrontimageContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where frontimage contains
        defaultAutocarejobFiltering("frontimage.contains=" + DEFAULT_FRONTIMAGE, "frontimage.contains=" + UPDATED_FRONTIMAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByFrontimageNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where frontimage does not contain
        defaultAutocarejobFiltering("frontimage.doesNotContain=" + UPDATED_FRONTIMAGE, "frontimage.doesNotContain=" + DEFAULT_FRONTIMAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLeftimageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where leftimage equals to
        defaultAutocarejobFiltering("leftimage.equals=" + DEFAULT_LEFTIMAGE, "leftimage.equals=" + UPDATED_LEFTIMAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLeftimageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where leftimage in
        defaultAutocarejobFiltering("leftimage.in=" + DEFAULT_LEFTIMAGE + "," + UPDATED_LEFTIMAGE, "leftimage.in=" + UPDATED_LEFTIMAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLeftimageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where leftimage is not null
        defaultAutocarejobFiltering("leftimage.specified=true", "leftimage.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLeftimageContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where leftimage contains
        defaultAutocarejobFiltering("leftimage.contains=" + DEFAULT_LEFTIMAGE, "leftimage.contains=" + UPDATED_LEFTIMAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByLeftimageNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where leftimage does not contain
        defaultAutocarejobFiltering("leftimage.doesNotContain=" + UPDATED_LEFTIMAGE, "leftimage.doesNotContain=" + DEFAULT_LEFTIMAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByRightimageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where rightimage equals to
        defaultAutocarejobFiltering("rightimage.equals=" + DEFAULT_RIGHTIMAGE, "rightimage.equals=" + UPDATED_RIGHTIMAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByRightimageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where rightimage in
        defaultAutocarejobFiltering(
            "rightimage.in=" + DEFAULT_RIGHTIMAGE + "," + UPDATED_RIGHTIMAGE,
            "rightimage.in=" + UPDATED_RIGHTIMAGE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByRightimageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where rightimage is not null
        defaultAutocarejobFiltering("rightimage.specified=true", "rightimage.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByRightimageContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where rightimage contains
        defaultAutocarejobFiltering("rightimage.contains=" + DEFAULT_RIGHTIMAGE, "rightimage.contains=" + UPDATED_RIGHTIMAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByRightimageNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where rightimage does not contain
        defaultAutocarejobFiltering("rightimage.doesNotContain=" + UPDATED_RIGHTIMAGE, "rightimage.doesNotContain=" + DEFAULT_RIGHTIMAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByBackimageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where backimage equals to
        defaultAutocarejobFiltering("backimage.equals=" + DEFAULT_BACKIMAGE, "backimage.equals=" + UPDATED_BACKIMAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByBackimageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where backimage in
        defaultAutocarejobFiltering("backimage.in=" + DEFAULT_BACKIMAGE + "," + UPDATED_BACKIMAGE, "backimage.in=" + UPDATED_BACKIMAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByBackimageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where backimage is not null
        defaultAutocarejobFiltering("backimage.specified=true", "backimage.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByBackimageContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where backimage contains
        defaultAutocarejobFiltering("backimage.contains=" + DEFAULT_BACKIMAGE, "backimage.contains=" + UPDATED_BACKIMAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByBackimageNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where backimage does not contain
        defaultAutocarejobFiltering("backimage.doesNotContain=" + UPDATED_BACKIMAGE, "backimage.doesNotContain=" + DEFAULT_BACKIMAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByDashboardimageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where dashboardimage equals to
        defaultAutocarejobFiltering("dashboardimage.equals=" + DEFAULT_DASHBOARDIMAGE, "dashboardimage.equals=" + UPDATED_DASHBOARDIMAGE);
    }

    @Test
    @Transactional
    void getAllAutocarejobsByDashboardimageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where dashboardimage in
        defaultAutocarejobFiltering(
            "dashboardimage.in=" + DEFAULT_DASHBOARDIMAGE + "," + UPDATED_DASHBOARDIMAGE,
            "dashboardimage.in=" + UPDATED_DASHBOARDIMAGE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByDashboardimageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where dashboardimage is not null
        defaultAutocarejobFiltering("dashboardimage.specified=true", "dashboardimage.specified=false");
    }

    @Test
    @Transactional
    void getAllAutocarejobsByDashboardimageContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where dashboardimage contains
        defaultAutocarejobFiltering(
            "dashboardimage.contains=" + DEFAULT_DASHBOARDIMAGE,
            "dashboardimage.contains=" + UPDATED_DASHBOARDIMAGE
        );
    }

    @Test
    @Transactional
    void getAllAutocarejobsByDashboardimageNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        // Get all the autocarejobList where dashboardimage does not contain
        defaultAutocarejobFiltering(
            "dashboardimage.doesNotContain=" + UPDATED_DASHBOARDIMAGE,
            "dashboardimage.doesNotContain=" + DEFAULT_DASHBOARDIMAGE
        );
    }

    private void defaultAutocarejobFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAutocarejobShouldBeFound(shouldBeFound);
        defaultAutocarejobShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAutocarejobShouldBeFound(String filter) throws Exception {
        restAutocarejobMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autocarejob.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobnumber").value(hasItem(DEFAULT_JOBNUMBER)))
            .andExpect(jsonPath("$.[*].vehicleid").value(hasItem(DEFAULT_VEHICLEID)))
            .andExpect(jsonPath("$.[*].vehiclenumber").value(hasItem(DEFAULT_VEHICLENUMBER)))
            .andExpect(jsonPath("$.[*].millage").value(hasItem(DEFAULT_MILLAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].nextmillage").value(hasItem(DEFAULT_NEXTMILLAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].nextservicedate").value(hasItem(DEFAULT_NEXTSERVICEDATE.toString())))
            .andExpect(jsonPath("$.[*].vehicletypeid").value(hasItem(DEFAULT_VEHICLETYPEID)))
            .andExpect(jsonPath("$.[*].jobtypeid").value(hasItem(DEFAULT_JOBTYPEID)))
            .andExpect(jsonPath("$.[*].jobtypename").value(hasItem(DEFAULT_JOBTYPENAME)))
            .andExpect(jsonPath("$.[*].jobopenby").value(hasItem(DEFAULT_JOBOPENBY)))
            .andExpect(jsonPath("$.[*].jobopentime").value(hasItem(DEFAULT_JOBOPENTIME.toString())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].specialrquirments").value(hasItem(DEFAULT_SPECIALRQUIRMENTS)))
            .andExpect(jsonPath("$.[*].specialinstructions").value(hasItem(DEFAULT_SPECIALINSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].nextserviceinstructions").value(hasItem(DEFAULT_NEXTSERVICEINSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].lastserviceinstructions").value(hasItem(DEFAULT_LASTSERVICEINSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].isadvisorchecked").value(hasItem(DEFAULT_ISADVISORCHECKED)))
            .andExpect(jsonPath("$.[*].isempallocated").value(hasItem(DEFAULT_ISEMPALLOCATED)))
            .andExpect(jsonPath("$.[*].jobclosetime").value(hasItem(DEFAULT_JOBCLOSETIME.toString())))
            .andExpect(jsonPath("$.[*].isjobclose").value(hasItem(DEFAULT_ISJOBCLOSE)))
            .andExpect(jsonPath("$.[*].isfeedback").value(hasItem(DEFAULT_ISFEEDBACK)))
            .andExpect(jsonPath("$.[*].feedbackstatusid").value(hasItem(DEFAULT_FEEDBACKSTATUSID)))
            .andExpect(jsonPath("$.[*].customername").value(hasItem(DEFAULT_CUSTOMERNAME)))
            .andExpect(jsonPath("$.[*].customertel").value(hasItem(DEFAULT_CUSTOMERTEL)))
            .andExpect(jsonPath("$.[*].customerid").value(hasItem(DEFAULT_CUSTOMERID)))
            .andExpect(jsonPath("$.[*].advisorfinalcheck").value(hasItem(DEFAULT_ADVISORFINALCHECK)))
            .andExpect(jsonPath("$.[*].jobdate").value(hasItem(DEFAULT_JOBDATE.toString())))
            .andExpect(jsonPath("$.[*].iscompanyservice").value(hasItem(DEFAULT_ISCOMPANYSERVICE)))
            .andExpect(jsonPath("$.[*].freeservicenumber").value(hasItem(DEFAULT_FREESERVICENUMBER)))
            .andExpect(jsonPath("$.[*].companyid").value(hasItem(DEFAULT_COMPANYID)))
            .andExpect(jsonPath("$.[*].updatetocustomer").value(hasItem(DEFAULT_UPDATETOCUSTOMER)))
            .andExpect(jsonPath("$.[*].nextgearoilmilage").value(hasItem(DEFAULT_NEXTGEAROILMILAGE)))
            .andExpect(jsonPath("$.[*].isjobinvoiced").value(hasItem(DEFAULT_ISJOBINVOICED)))
            .andExpect(jsonPath("$.[*].iswaiting").value(hasItem(DEFAULT_ISWAITING)))
            .andExpect(jsonPath("$.[*].iscustomercomment").value(hasItem(DEFAULT_ISCUSTOMERCOMMENT)))
            .andExpect(jsonPath("$.[*].imagefolder").value(hasItem(DEFAULT_IMAGEFOLDER)))
            .andExpect(jsonPath("$.[*].frontimage").value(hasItem(DEFAULT_FRONTIMAGE)))
            .andExpect(jsonPath("$.[*].leftimage").value(hasItem(DEFAULT_LEFTIMAGE)))
            .andExpect(jsonPath("$.[*].rightimage").value(hasItem(DEFAULT_RIGHTIMAGE)))
            .andExpect(jsonPath("$.[*].backimage").value(hasItem(DEFAULT_BACKIMAGE)))
            .andExpect(jsonPath("$.[*].dashboardimage").value(hasItem(DEFAULT_DASHBOARDIMAGE)));

        // Check, that the count call also returns 1
        restAutocarejobMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAutocarejobShouldNotBeFound(String filter) throws Exception {
        restAutocarejobMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAutocarejobMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAutocarejob() throws Exception {
        // Get the autocarejob
        restAutocarejobMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAutocarejob() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autocarejob
        Autocarejob updatedAutocarejob = autocarejobRepository.findById(autocarejob.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAutocarejob are not directly saved in db
        em.detach(updatedAutocarejob);
        updatedAutocarejob
            .jobnumber(UPDATED_JOBNUMBER)
            .vehicleid(UPDATED_VEHICLEID)
            .vehiclenumber(UPDATED_VEHICLENUMBER)
            .millage(UPDATED_MILLAGE)
            .nextmillage(UPDATED_NEXTMILLAGE)
            .nextservicedate(UPDATED_NEXTSERVICEDATE)
            .vehicletypeid(UPDATED_VEHICLETYPEID)
            .jobtypeid(UPDATED_JOBTYPEID)
            .jobtypename(UPDATED_JOBTYPENAME)
            .jobopenby(UPDATED_JOBOPENBY)
            .jobopentime(UPDATED_JOBOPENTIME)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .specialrquirments(UPDATED_SPECIALRQUIRMENTS)
            .specialinstructions(UPDATED_SPECIALINSTRUCTIONS)
            .remarks(UPDATED_REMARKS)
            .nextserviceinstructions(UPDATED_NEXTSERVICEINSTRUCTIONS)
            .lastserviceinstructions(UPDATED_LASTSERVICEINSTRUCTIONS)
            .isadvisorchecked(UPDATED_ISADVISORCHECKED)
            .isempallocated(UPDATED_ISEMPALLOCATED)
            .jobclosetime(UPDATED_JOBCLOSETIME)
            .isjobclose(UPDATED_ISJOBCLOSE)
            .isfeedback(UPDATED_ISFEEDBACK)
            .feedbackstatusid(UPDATED_FEEDBACKSTATUSID)
            .customername(UPDATED_CUSTOMERNAME)
            .customertel(UPDATED_CUSTOMERTEL)
            .customerid(UPDATED_CUSTOMERID)
            .advisorfinalcheck(UPDATED_ADVISORFINALCHECK)
            .jobdate(UPDATED_JOBDATE)
            .iscompanyservice(UPDATED_ISCOMPANYSERVICE)
            .freeservicenumber(UPDATED_FREESERVICENUMBER)
            .companyid(UPDATED_COMPANYID)
            .updatetocustomer(UPDATED_UPDATETOCUSTOMER)
            .nextgearoilmilage(UPDATED_NEXTGEAROILMILAGE)
            .isjobinvoiced(UPDATED_ISJOBINVOICED)
            .iswaiting(UPDATED_ISWAITING)
            .iscustomercomment(UPDATED_ISCUSTOMERCOMMENT)
            .imagefolder(UPDATED_IMAGEFOLDER)
            .frontimage(UPDATED_FRONTIMAGE)
            .leftimage(UPDATED_LEFTIMAGE)
            .rightimage(UPDATED_RIGHTIMAGE)
            .backimage(UPDATED_BACKIMAGE)
            .dashboardimage(UPDATED_DASHBOARDIMAGE);

        restAutocarejobMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAutocarejob.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAutocarejob))
            )
            .andExpect(status().isOk());

        // Validate the Autocarejob in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAutocarejobToMatchAllProperties(updatedAutocarejob);
    }

    @Test
    @Transactional
    void putNonExistingAutocarejob() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autocarejob.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutocarejobMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autocarejob.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autocarejob))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autocarejob in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutocarejob() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autocarejob.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutocarejobMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autocarejob))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autocarejob in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutocarejob() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autocarejob.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutocarejobMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autocarejob)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autocarejob in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutocarejobWithPatch() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autocarejob using partial update
        Autocarejob partialUpdatedAutocarejob = new Autocarejob();
        partialUpdatedAutocarejob.setId(autocarejob.getId());

        partialUpdatedAutocarejob
            .jobnumber(UPDATED_JOBNUMBER)
            .vehicleid(UPDATED_VEHICLEID)
            .vehiclenumber(UPDATED_VEHICLENUMBER)
            .millage(UPDATED_MILLAGE)
            .nextservicedate(UPDATED_NEXTSERVICEDATE)
            .vehicletypeid(UPDATED_VEHICLETYPEID)
            .jobtypename(UPDATED_JOBTYPENAME)
            .jobopenby(UPDATED_JOBOPENBY)
            .specialrquirments(UPDATED_SPECIALRQUIRMENTS)
            .remarks(UPDATED_REMARKS)
            .nextserviceinstructions(UPDATED_NEXTSERVICEINSTRUCTIONS)
            .lastserviceinstructions(UPDATED_LASTSERVICEINSTRUCTIONS)
            .isadvisorchecked(UPDATED_ISADVISORCHECKED)
            .isempallocated(UPDATED_ISEMPALLOCATED)
            .isjobclose(UPDATED_ISJOBCLOSE)
            .isfeedback(UPDATED_ISFEEDBACK)
            .customername(UPDATED_CUSTOMERNAME)
            .customertel(UPDATED_CUSTOMERTEL)
            .advisorfinalcheck(UPDATED_ADVISORFINALCHECK)
            .companyid(UPDATED_COMPANYID)
            .isjobinvoiced(UPDATED_ISJOBINVOICED)
            .iswaiting(UPDATED_ISWAITING)
            .iscustomercomment(UPDATED_ISCUSTOMERCOMMENT)
            .imagefolder(UPDATED_IMAGEFOLDER)
            .frontimage(UPDATED_FRONTIMAGE);

        restAutocarejobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutocarejob.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutocarejob))
            )
            .andExpect(status().isOk());

        // Validate the Autocarejob in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutocarejobUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAutocarejob, autocarejob),
            getPersistedAutocarejob(autocarejob)
        );
    }

    @Test
    @Transactional
    void fullUpdateAutocarejobWithPatch() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autocarejob using partial update
        Autocarejob partialUpdatedAutocarejob = new Autocarejob();
        partialUpdatedAutocarejob.setId(autocarejob.getId());

        partialUpdatedAutocarejob
            .jobnumber(UPDATED_JOBNUMBER)
            .vehicleid(UPDATED_VEHICLEID)
            .vehiclenumber(UPDATED_VEHICLENUMBER)
            .millage(UPDATED_MILLAGE)
            .nextmillage(UPDATED_NEXTMILLAGE)
            .nextservicedate(UPDATED_NEXTSERVICEDATE)
            .vehicletypeid(UPDATED_VEHICLETYPEID)
            .jobtypeid(UPDATED_JOBTYPEID)
            .jobtypename(UPDATED_JOBTYPENAME)
            .jobopenby(UPDATED_JOBOPENBY)
            .jobopentime(UPDATED_JOBOPENTIME)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .specialrquirments(UPDATED_SPECIALRQUIRMENTS)
            .specialinstructions(UPDATED_SPECIALINSTRUCTIONS)
            .remarks(UPDATED_REMARKS)
            .nextserviceinstructions(UPDATED_NEXTSERVICEINSTRUCTIONS)
            .lastserviceinstructions(UPDATED_LASTSERVICEINSTRUCTIONS)
            .isadvisorchecked(UPDATED_ISADVISORCHECKED)
            .isempallocated(UPDATED_ISEMPALLOCATED)
            .jobclosetime(UPDATED_JOBCLOSETIME)
            .isjobclose(UPDATED_ISJOBCLOSE)
            .isfeedback(UPDATED_ISFEEDBACK)
            .feedbackstatusid(UPDATED_FEEDBACKSTATUSID)
            .customername(UPDATED_CUSTOMERNAME)
            .customertel(UPDATED_CUSTOMERTEL)
            .customerid(UPDATED_CUSTOMERID)
            .advisorfinalcheck(UPDATED_ADVISORFINALCHECK)
            .jobdate(UPDATED_JOBDATE)
            .iscompanyservice(UPDATED_ISCOMPANYSERVICE)
            .freeservicenumber(UPDATED_FREESERVICENUMBER)
            .companyid(UPDATED_COMPANYID)
            .updatetocustomer(UPDATED_UPDATETOCUSTOMER)
            .nextgearoilmilage(UPDATED_NEXTGEAROILMILAGE)
            .isjobinvoiced(UPDATED_ISJOBINVOICED)
            .iswaiting(UPDATED_ISWAITING)
            .iscustomercomment(UPDATED_ISCUSTOMERCOMMENT)
            .imagefolder(UPDATED_IMAGEFOLDER)
            .frontimage(UPDATED_FRONTIMAGE)
            .leftimage(UPDATED_LEFTIMAGE)
            .rightimage(UPDATED_RIGHTIMAGE)
            .backimage(UPDATED_BACKIMAGE)
            .dashboardimage(UPDATED_DASHBOARDIMAGE);

        restAutocarejobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutocarejob.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutocarejob))
            )
            .andExpect(status().isOk());

        // Validate the Autocarejob in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutocarejobUpdatableFieldsEquals(partialUpdatedAutocarejob, getPersistedAutocarejob(partialUpdatedAutocarejob));
    }

    @Test
    @Transactional
    void patchNonExistingAutocarejob() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autocarejob.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutocarejobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autocarejob.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autocarejob))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autocarejob in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutocarejob() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autocarejob.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutocarejobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autocarejob))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autocarejob in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutocarejob() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autocarejob.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutocarejobMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(autocarejob)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autocarejob in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutocarejob() throws Exception {
        // Initialize the database
        insertedAutocarejob = autocarejobRepository.saveAndFlush(autocarejob);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the autocarejob
        restAutocarejobMockMvc
            .perform(delete(ENTITY_API_URL_ID, autocarejob.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return autocarejobRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Autocarejob getPersistedAutocarejob(Autocarejob autocarejob) {
        return autocarejobRepository.findById(autocarejob.getId()).orElseThrow();
    }

    protected void assertPersistedAutocarejobToMatchAllProperties(Autocarejob expectedAutocarejob) {
        assertAutocarejobAllPropertiesEquals(expectedAutocarejob, getPersistedAutocarejob(expectedAutocarejob));
    }

    protected void assertPersistedAutocarejobToMatchUpdatableProperties(Autocarejob expectedAutocarejob) {
        assertAutocarejobAllUpdatablePropertiesEquals(expectedAutocarejob, getPersistedAutocarejob(expectedAutocarejob));
    }
}
