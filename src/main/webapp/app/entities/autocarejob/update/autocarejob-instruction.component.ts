import { Component, inject, OnInit, ChangeDetectorRef, ViewChild, Input } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAutocarejob } from '../autocarejob.model';
import { ICustomervehicle } from 'app/entities/customervehicle/customervehicle.model';
import { CustomervehicleService } from 'app/entities/customervehicle/service/customervehicle.service';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { VehiclebrandmodelService } from 'app/entities/vehiclebrandmodel/service/vehiclebrandmodel.service';
import { IVehiclebrandmodel } from 'app/entities/vehiclebrandmodel/vehiclebrandmodel.model';
import { VehiclebrandnameService } from 'app/entities/vehiclebrandname/service/vehiclebrandname.service';
import { IVehiclebrandname } from 'app/entities/vehiclebrandname/vehiclebrandname.model';
import { ServicecategoryService } from 'app/entities/servicecategory/service/servicecategory.service';
import { IServicecategory } from 'app/entities/servicecategory/servicecategory.model';
import { ServicesubcategoryService } from 'app/entities/servicesubcategory/service/servicesubcategory.service';
import { IServicesubcategory } from 'app/entities/servicesubcategory/servicesubcategory.model';
import { InventoryService } from 'app/entities/inventory/service/inventory.service';
import { AutocarejobService } from '../service/autocarejob.service';
import { AutocarejobFormService, AutocarejobFormGroup } from './autocarejob-form.service';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { ICommonserviceoption } from 'app/entities/commonserviceoption/commonserviceoption.model';
import { CommonserviceoptionService } from 'app/entities/commonserviceoption/service/commonserviceoption.service';
import { IBillingserviceoption } from 'app/entities/billingserviceoption/billingserviceoption.model';
import { BillingserviceoptionService } from 'app/entities/billingserviceoption/service/billingserviceoption.service';
import { IBillingserviceoptionvalues } from 'app/entities/billingserviceoptionvalues/billingserviceoptionvalues.model';
import { IAutojobsinvoice } from 'app/entities/autojobsinvoice/autojobsinvoice.model';
import { BillingserviceoptionvaluesService } from 'app/entities/billingserviceoptionvalues/service/billingserviceoptionvalues.service';
import { IVehicletype } from 'app/entities/vehicletype/vehicletype.model';
import { VehicletypeService } from 'app/entities/vehicletype/service/vehicletype.service';
import { AutojobsinvoicelinesService } from 'app/entities/autojobsinvoicelines/service/autojobsinvoicelines.service';
import { IWorkshopworklist } from 'app/entities/workshopworklist/workshopworklist.model';
import { WorkshopworklistService } from 'app/entities/workshopworklist/service/workshopworklist.service';
import { AutojobsinvoiceUpdateComponent } from 'app/entities/autojobsinvoice/update/autojobsinvoice-update.component';
import { AutocarejobUpdateComponent } from './autocarejob-update.component';
import { AutojobsaleinvoicecommonservicechargeUpdateComponent } from 'app/entities/autojobsaleinvoicecommonservicecharge/update/autojobsaleinvoicecommonservicecharge-update.component';
import { AutojobsinvoicelinesUpdateComponent } from 'app/entities/autojobsinvoicelines/update/autojobsinvoicelines-update.component';
import { AutojobsalesinvoiceservicechargelineUpdateComponent } from 'app/entities/autojobsalesinvoiceservicechargeline/update/autojobsalesinvoiceservicechargeline-update.component';
import { WorkshopVehicleWorkListUpdateComponent } from 'app/entities/workshop-vehicle-work-list/update/workshop-vehicle-work-list-update.component';
import { IAutojobsaleinvoicecommonservicecharge } from 'app/entities/autojobsaleinvoicecommonservicecharge/autojobsaleinvoicecommonservicecharge.model';
import { WorkshopvehicleworkUpdateComponent } from 'app/entities/workshopvehiclework/update/workshopvehiclework-update.component';
import { AutojobsalesinvoiceservicechargelineService } from 'app/entities/autojobsalesinvoiceservicechargeline/service/autojobsalesinvoiceservicechargeline.service';

import { AutojobsaleinvoicecommonservicechargeService } from 'app/entities/autojobsaleinvoicecommonservicecharge/service/autojobsaleinvoicecommonservicecharge.service';
import { IWorkshopvehiclework } from 'app/entities/workshopvehiclework/workshopvehiclework.model';
import { SalesInvoiceLinesDummyService } from 'app/entities/sales-invoice-lines-dummy/service/sales-invoice-lines-dummy.service';

@Component({
  standalone: true,
  selector: 'jhi-autocarejob-instruction',
  templateUrl: './autocarejob-instruction.component.html',
  imports: [
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    AutojobsinvoiceUpdateComponent,
    AutojobsaleinvoicecommonservicechargeUpdateComponent,
    AutojobsinvoicelinesUpdateComponent,
    AutojobsalesinvoiceservicechargelineUpdateComponent,
    WorkshopVehicleWorkListUpdateComponent,
    WorkshopvehicleworkUpdateComponent,

    AutocarejobUpdateComponent,
  ],
})
export class AutocarejobInstructionComponent implements OnInit {
  @Input() invid: number = 0;
  @ViewChild(AutojobsinvoiceUpdateComponent) autojobsinvoiceComponent!: AutojobsinvoiceUpdateComponent;
  @ViewChild(WorkshopVehicleWorkListUpdateComponent) workshopVehicleWorkListComponent!: WorkshopVehicleWorkListUpdateComponent;
  @ViewChild(AutojobsaleinvoicecommonservicechargeUpdateComponent)
  autojobsaleinvoicecommonservicechargeComponent!: AutojobsaleinvoicecommonservicechargeUpdateComponent;
  @ViewChild(AutojobsinvoicelinesUpdateComponent) autojobsinvoicelinesComponent!: AutojobsinvoicelinesUpdateComponent;
  jobinvoicelines = inject(AutojobsinvoicelinesService);
  searchname = inject(SalesInvoiceLinesDummyService);
  jobcommon = inject(AutojobsaleinvoicecommonservicechargeService);
  jobservice = inject(AutojobsalesinvoiceservicechargelineService);
  @ViewChild(AutocarejobUpdateComponent) autocarejobComponent!: AutocarejobUpdateComponent;
  @ViewChild(AutojobsalesinvoiceservicechargelineUpdateComponent)
  autojobsalesinvoiceservicechargelineComponent!: AutojobsalesinvoiceservicechargelineUpdateComponent;
  constructor(private cdr: ChangeDetectorRef) {} // Inject ChangeDetectorRef
  isSaving = false;
  autocarejob: IAutocarejob | null = null;
  customervehicles: ICustomervehicle[] = [];
  customerDetails: any | null = null;
  vehiclebrandmodel: IVehiclebrandmodel[] = [];
  vehiclebrandname: IVehiclebrandname[] = [];
  servicecategory: IServicecategory[] = [];
  servicesubcategory: IServicesubcategory[] = [];
  inventory: IInventory[] = [];
  commonserviceoption: ICommonserviceoption[] = [];
  billingserviceoption: IBillingserviceoption[] = [];
  // selectedcommonServices: ICommonserviceoption[] = [];
  totalcommonServiceCharge = 0;
  billingserviceoptionvalues: IBillingserviceoptionvalues[] = [];
  vehicletypes: IVehicletype[] = [];
  selectedVehicleTypeId: number | null = null;
  filteredBillingServiceOptionValues: IBillingserviceoptionvalues[] = [];
  workshopworklist: IWorkshopworklist[] = [];
  selectedworkItems: IWorkshopworklist[] = [];

  // Variables for service selection and total calculation
  selectedServices: IBillingserviceoptionvalues[] = [];
  totalServiceCharge: number = 0;

  protected autocarejobService = inject(AutocarejobService);
  protected autocarejobFormService = inject(AutocarejobFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected customervehicleService = inject(CustomervehicleService);
  protected customerService = inject(CustomerService);
  protected vehiclebrandmodelService = inject(VehiclebrandmodelService);
  protected vehiclebrandnameService = inject(VehiclebrandnameService);
  protected servicecategoryService = inject(ServicecategoryService);
  protected servicesubcategoryService = inject(ServicesubcategoryService);
  protected inventoryService = inject(InventoryService);
  protected commonserviceoptionService = inject(CommonserviceoptionService);
  protected billingserviceoptionService = inject(BillingserviceoptionService);
  protected billingserviceoptionvaluesService = inject(BillingserviceoptionvaluesService);
  protected vehicletypesService = inject(VehicletypeService);
  protected workshopworklistService = inject(WorkshopworklistService);

  subcategoriesVisible = false; // Controls whether subcategories are shown or hidden
  toggleSubcategories() {
    this.subcategoriesVisible = !this.subcategoriesVisible;
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutocarejobFormGroup = this.autocarejobFormService.createAutocarejobFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autocarejob }) => {
      this.autocarejob = autocarejob;
      if (autocarejob) {
        this.updateForm(autocarejob);
      }

      this.loadDataFromOtherEntities();
      this.loadDataFromBrandEntities();
      this.loadDataFromServicesEntities();
      this.loadDataFromServicessubEntities();
      this.loadDataFromCommonServiceOptionEntities();

      this.loadDataFromBillingServiceOptionValuesEntities();
      this.loadVehicleTypes();
      this.loadBillingServiceOptions();
      this.loadBillingServiceOptionValues();
      this.loadDataFromWorkshopWorklistEntities();
      this.setAutoNextServiceDate();
    });
  }

  setAutoNextServiceDate(): void {
    const futureDate = dayjs().add(6, 'month').format('YYYY-MM-DD'); // Add 6 months to today
    this.editForm.patchValue({ nextservicedate: dayjs(futureDate) }); // Update form
    this.cdr.detectChanges(); // Ensure UI updates
  }
  previousState(): void {
    window.history.back();
  }

  loadVehicleTypes(): void {
    this.vehicletypesService.query({ size: 1000 }).subscribe((res: HttpResponse<IVehicletype[]>) => {
      this.vehicletypes = res.body || [];
    });
  }

  loadBillingServiceOptions(): void {
    let page = 0;
    const pageSize = 20;
    this.billingserviceoption = [];

    const fetchPage = () => {
      this.billingserviceoptionService.query({ page, size: pageSize }).subscribe(
        (res: HttpResponse<IBillingserviceoption[]>) => {
          this.billingserviceoption = [...this.billingserviceoption, ...(res.body || [])];

          const totalItems = res.headers.get('X-Total-Count');
          const totalRecords = totalItems ? parseInt(totalItems, 10) : 0;

          if (this.billingserviceoption.length < totalRecords) {
            page++;
            fetchPage();
          } else {
          }
        },
        error => {},
      );
    };

    fetchPage();
  }

  billingOptionsByVehicleType: { [key: number]: IBillingserviceoptionvalues[] } = {};

  loadBillingServiceOptionValues(): void {
    let page = 0;
    const pageSize = 20;
    this.billingserviceoptionvalues = [];

    const fetchPage = () => {
      this.billingserviceoptionvaluesService.query({ page, size: pageSize }).subscribe(
        (res: HttpResponse<IBillingserviceoptionvalues[]>) => {
          this.billingserviceoptionvalues = [...this.billingserviceoptionvalues, ...(res.body || [])];

          const totalItems = res.headers.get('X-Total-Count');
          const totalRecords = totalItems ? parseInt(totalItems, 10) : 0;

          if (this.billingserviceoptionvalues.length < totalRecords) {
            page++;
            fetchPage();
          } else {
            this.createBillingOptionsLookup();
            this.filterBillingServiceOptionValues();
          }
        },
        error => {},
      );
    };

    fetchPage();
  }

  createBillingOptionsLookup(): void {
    this.billingOptionsByVehicleType = {};

    this.billingserviceoptionvalues.forEach(value => {
      if (value.vehicletypeid != null && !this.billingOptionsByVehicleType[value.vehicletypeid]) {
        this.billingOptionsByVehicleType[value.vehicletypeid] = [];
      }
      if (value.vehicletypeid != null) {
        this.billingOptionsByVehicleType[value.vehicletypeid].push(value);
      }
    });

    console.log('Billing Options Lookup:', this.billingOptionsByVehicleType); // Debugging
  }

  filterBillingServiceOptionValues(): void {
    if (this.selectedVehicleTypeId) {
      this.filteredBillingServiceOptionValues = this.billingOptionsByVehicleType[this.selectedVehicleTypeId] || [];
    } else {
      this.filteredBillingServiceOptionValues = this.billingserviceoptionvalues;
    }

    console.log('Filtered Billing Service Option Values:', this.filteredBillingServiceOptionValues); // Debugging
  }

  getBillingServiceOptionName(billingserviceoptionId: number | null | undefined): string {
    if (billingserviceoptionId == null) {
      return 'Unknown';
    }

    const option = this.billingserviceoption.find(opt => opt.id === billingserviceoptionId);
    // console.log('Billing Service Optionssssssssss:', option); // Debugging
    return option && option.servicename ? option.servicename : 'Unknown';
  }

  onVehicleTypeChange(): void {
    this.filterBillingServiceOptionValues();
    this.calculateTotalCharge();
  }

  isServiceSelected(item: IBillingserviceoptionvalues): boolean {
    return this.selectedServices.some(service => service.id === item.id);
  }

  // Define the serviceArray to hold the selected service details
  serviceArray: Array<{
    invoiceid: number;
    lineid: number;
    optionid: number;
    servicename: string;
    servicediscription: string;
    value: number;
    addedbyid: number;
    iscustomersrvice: boolean;
    discount: number;
    serviceprice: number;
  }> = [];

  onServiceSelectionChanges(item: IBillingserviceoptionvalues, event: any): void {
    const serviceName = this.getBillingServiceOptionName(item.billingserviceoptionid);

    if (event.target.checked) {
      // Check if the service already exists in the selectedServices array
      const nextLineId = this.serviceArray.length > 0 ? Math.max(...this.serviceArray.map(item => item.lineid), 0) + 1 : 1;
      const nextOpId = this.serviceArray.length > 0 ? Math.max(...this.serviceArray.map(item => item.lineid), 0) + 1 : 1;
      const exists = this.selectedServices.some(service => service.billingserviceoptionid === item.billingserviceoptionid);

      if (!exists) {
        // Add the service to selectedServices with the service name and value
        this.selectedServices.push({
          ...item,
          servicename: serviceName,
        });

        // Add the service to serviceArray with the required structure
        this.serviceArray.push({
          invoiceid: 0,
          lineid: nextLineId,
          optionid: nextOpId,
          servicename: serviceName,
          servicediscription: '',
          value: item.value ?? 0,
          addedbyid: 0,
          iscustomersrvice: true,
          discount: 0,
          serviceprice: 0,
        });
      }
    } else {
      // Remove the service by filtering it out
      this.selectedServices = this.selectedServices.filter(service => service.billingserviceoptionid !== item.billingserviceoptionid);

      // Remove the service from serviceArray by filtering it out
      this.serviceArray = this.serviceArray.filter(service => service.optionid !== item.billingserviceoptionid);
    }

    this.calculateTotalCharges(); // Ensure this is called

    // console.log('Selected Services:', this.selectedServices);
    // console.log('serviceArrayyyyyyyyyyyyyyyy:', this.serviceArray);

    this.selectedServices.forEach(service => {
      // console.log('Selected Service billingserviceoptionid:', service.billingserviceoptionid);
      // console.log('Service Name:', service.servicename);
    });

    // const selectedServiceNames = this.selectedServices.map(s => s.servicename);
    // console.log('Selected Service Names:', selectedServiceNames);
  }

  onAmountChange(item: IBillingserviceoptionvalues): void {
    console.log(`Updated Amount for Service ID ${item.id}:`, item.value);
    this.calculateTotalCharges(); // Recalculate the total when the amount is changed
  }

  calculateTotalCharges(): void {
    this.totalServiceCharge = this.selectedServices.reduce((sum, service) => sum + (service.value || 0), 0);
    console.log('Total Service Charge:', this.totalServiceCharge);
  }

  loadDataFromOtherEntities() {
    this.vehiclebrandmodelService.query().subscribe((res: any) => {
      this.vehiclebrandmodel = res.body;
    });
  }

  loadDataFromBrandEntities() {
    this.vehiclebrandnameService.query().subscribe((res: any) => {
      this.vehiclebrandname = res.body;
    });
  }

  loadDataFromCommonServiceOptionEntities() {
    this.commonserviceoptionService.query().subscribe((res: any) => {
      this.commonserviceoption = res.body;
    });
  }
  // Define the new array with the given structure
  commonServiceArray: Array<{
    invoiceid: number;
    lineid: number;
    optionid: number;
    mainid: number;
    code: string;
    name: string;
    description: string;
    value: number;
    addedbyid: number;
    discount: number;
    serviceprice: number;
  }> = [];

  selectedcommonServices: Array<ICommonserviceoption> = []; // Assuming this is defined elsewhere

  onServiceSelectionChange(service: ICommonserviceoption, event: any) {
    if (event.target.checked) {
      // Add the selected item to the list with the required fields and default values
      const nextLineId = this.itemsArray.length > 0 ? Math.max(...this.itemsArray.map(item => item.lineid), 0) + 1 : 1;
      // Add service to selectedcommonServices if not already added
      this.selectedcommonServices.push(service);

      this.commonServiceArray.push({
        invoiceid: 0,
        lineid: nextLineId,
        optionid: service.id,
        mainid: 0,
        code: service.code || '',
        name: service.name || '',
        description: service.description || 'No description',
        value: service.value ?? 0,
        addedbyid: 0,
        discount: 0,
        serviceprice: 0,
      });
    } else {
      // Remove service from selectedcommonServices if unchecked
      this.selectedcommonServices = this.selectedcommonServices.filter(s => s.id !== service.id);

      // Remove service from commonServiceArray
      this.commonServiceArray = this.commonServiceArray.filter(s => s.optionid !== service.id);
    }

    console.log('Selected Common Services:', this.selectedcommonServices);
    console.log('Common Service Arrayyyy:', this.commonServiceArray);

    // Ensure total charge is recalculated
    this.calculateTotalCharge();
  }

  calculateTotalCharge() {
    this.totalcommonServiceCharge = this.selectedcommonServices.reduce((sum, service) => sum + (service.value || 0), 0);
    this.cdr.detectChanges(); // Ensure UI updates
  }

  loadDataFromServicesEntities() {
    this.servicecategoryService.query({ size: 1000 }).subscribe((res: any) => {
      this.servicecategory = res.body;
    });
  }

  // Get unique main categories from servicesubcategory
  getUniqueMainCategories(): string[] {
    if (!this.servicesubcategory) return [];
    const mainCategories = this.servicesubcategory.map(item => item.mainname).filter((name): name is string => name != null);
    return [...new Set(mainCategories)]; // Remove duplicates
  }

  // Get subcategories by main category
  getSubCategoriesByMainCategory(mainCategory: string): IServicesubcategory[] {
    if (!this.servicesubcategory) return [];
    return this.servicesubcategory.filter(item => item.mainname === mainCategory);
  }

  // Load servicesubcategory data
  loadDataFromServicessubEntities(): void {
    this.servicesubcategoryService.query({ size: 1000 }).subscribe((res: HttpResponse<IServicesubcategory[]>) => {
      this.servicesubcategory = res.body || [];
    });
  }

  onSubcategorySelectionChange(subcategory: IServicesubcategory, event: any): void {
    if (event.target.checked) {
      // Add the subcategory to the selected list
      this.selectedworkItems.push(subcategory);
    } else {
      // Remove the subcategory from the selected list
      this.selectedworkItems = this.selectedworkItems.filter(item => item.id !== subcategory.id);
    }
    console.log('Selected Subcategories:', this.selectedworkItems);
  }

  loadDataFromWorkshopWorklistEntities() {
    this.workshopworklistService.query({ size: 1000 }).subscribe((res: any) => {
      this.workshopworklist = res.body;
    });
  }

  onworkServiceSelectionChange(item: any, event: any): void {
    if (event.target.checked) {
      this.selectedworkItems.push(item);
    } else {
      this.selectedworkItems = this.selectedworkItems.filter(selectedworkItem => selectedworkItem !== item);
    }
  }

  loadDataFromBillingServiceOptionValuesEntities() {
    this.billingserviceoptionvaluesService.query().subscribe((res: any) => {
      this.billingserviceoptionvalues = res.body;
    });
  }

  filteredVehicles: ICustomervehicle[] = [];

  onVehicleSearch(event: Event): void {
    const input = event.target as HTMLInputElement;
    const searchTerm = input.value;

    if (searchTerm.length > 2) {
      // Use the new service method to fetch matching results
      this.customervehicleService.findByVehicleNumber(searchTerm).subscribe(response => {
        this.filteredVehicles = response.body || [];
      });
    } else {
      // Clear the suggestions if input is too short
      this.filteredVehicles = [];
    }
  }

  filtereditems: IInventory[] = [];
  selectedItems: Array<IInventory & { discountPercentage: number; requestedQuantity: number }> = [];

  onItemSearch(event: Event): void {
    const input = event.target as HTMLInputElement;
    const searchTerm = input.value;

    if (searchTerm.length > 1) {
      // Use the new service method to fetch matching results
      console.log(searchTerm);
      this.searchname.getElementsByUserInputName(searchTerm).subscribe(response => {
        this.filtereditems = response.body || [];
        console.log('Filtered Itemsssssssss:', this.filtereditems);
      });
    } else {
      // Clear the suggestions if input is too short
      this.filtereditems = [];
    }
  }
  itemsArray: Array<{
    id: number;
    invocieid: number;
    lineid: number;
    itemid: number;
    itemcode: string;
    itemname: string;
    description: string;
    unitofmeasurement: string;
    quantity: number;
    itemcost: number;
    itemprice: number;
    discount: number;
    tax: number;
    sellingprice: number;
    linetotal: number;
    lmu: number;
    lmd: string;
    nbt: boolean;
    vat: boolean;
  }> = [];

  onAddItem(): void {
    const selectedItem = this.filtereditems.find(item => item.name === (document.getElementById('field_item') as HTMLInputElement).value);

    if (selectedItem) {
      // Add the selected item to the list with the required fields and default values
      const nextLineId = this.itemsArray.length > 0 ? Math.max(...this.itemsArray.map(item => item.lineid), 0) + 1 : 1;
      this.itemsArray.push({
        id: 0,
        itemid: selectedItem.id,
        invocieid: 0,
        lineid: nextLineId,
        itemcode: selectedItem.code ?? '',
        itemname: selectedItem.name ?? '',
        description: selectedItem.description ?? '',
        unitofmeasurement: selectedItem.unitofmeasurement ?? '',
        quantity: 1,
        itemcost: 0,
        itemprice: 0,
        discount: 0,
        tax: 0,
        sellingprice: selectedItem.lastsellingprice ?? 0,
        linetotal: selectedItem.lastsellingprice ?? 0,
        lmu: selectedItem.lmu ?? 0,
        lmd: selectedItem.lmd ? selectedItem.lmd.toString() : '',
        nbt: false,
        vat: false,
      });
      this.selectedItems.push({
        ...selectedItem,
        discountPercentage: 0,
        requestedQuantity: 1,
      });
      // Clear the search input and suggestions
      (document.getElementById('field_item') as HTMLInputElement).value = '';
      this.filtereditems = [];
    }

    console.log('Selected Items Arrayyyyuuu:', this.itemsArray);
  }

  onDeleteItem(index: number): void {
    // Remove the item from the list
    this.selectedItems.splice(index, 1);
    this.updateItemTotal(); // Update totals after deletion
  }

  calculateItemTotal(item: IInventory & { discountPercentage: number; requestedQuantity: number }): number {
    const price = item.lastsellingprice || 0;
    const discountAmount = (price * item.discountPercentage) / 100;
    const discountedPrice = price - discountAmount;

    console.log(
      `Item Total: ${discountedPrice * item.requestedQuantity} (Price: ${price}, Discount: ${discountAmount}, Qty: ${item.requestedQuantity})`,
    );

    return discountedPrice * item.requestedQuantity;
  }

  calculateTotalWithoutDiscount(): number {
    return this.selectedItems.reduce((total, item) => total + (item.lastsellingprice || 0) * item.requestedQuantity, 0);
  }

  calculateTotalDiscount(): number {
    return this.selectedItems.reduce(
      (total, item) => total + ((item.lastsellingprice || 0) * item.discountPercentage * item.requestedQuantity) / 100,
      0,
    );
  }

  calculateTotalWithDiscount(): number {
    return this.calculateTotalWithoutDiscount() - this.calculateTotalDiscount();
  }

  // Update the item total when discount or requested quantity changes
  updateItemTotal(): void {
    // Recalculate totals
    this.calculateTotalWithoutDiscount();
    this.calculateTotalDiscount();
    this.calculateTotalWithDiscount();

    // Ensure Angular updates UI
    this.cdr.detectChanges();
  }

  searchedCustomer: ICustomer | null = null;

  onVehicleSelect(event: Event): void {
    const input = event.target as HTMLInputElement;
    const selectedVehicleNumber = input.value;

    const selectedVehicle = this.filteredVehicles.find(vehicle => vehicle.vehiclenumber === selectedVehicleNumber);

    if (selectedVehicle && selectedVehicle.customerid != null) {
      this.customerService.find(selectedVehicle.customerid).subscribe(res => {
        this.searchedCustomer = res.body;
        console.log(this.searchedCustomer?.fullname);
        this.editForm.get('customername')?.patchValue(this.searchedCustomer?.fullname);
        this.editForm.get('customertel')?.patchValue(this.searchedCustomer?.residencephone);
      });
    } else {
      console.error('Invalid customer ID:', selectedVehicle);
    }
  }

  nextmillage: number | null = null;
  selectedRadioValue: number | null = null;

  // Handle changes in the millage input field
  onMillageChange(event: Event): void {
    const millage = +(event.target as HTMLInputElement).value;
    this.calculateNextMillage(millage ?? 0, this.selectedRadioValue);
  }

  // Handle changes in the radio button selection
  onRadioButtonChange(event: Event): void {
    const selectedValue = +(event.target as HTMLInputElement).value;
    this.selectedRadioValue = selectedValue;
    const millage = this.editForm.get('millage')?.value;
    this.calculateNextMillage(millage ?? 0, selectedValue);
  }

  // Calculate the next millage value
  calculateNextMillage(millage: number, selectedValue: number | null): void {
    if (millage && selectedValue) {
      this.nextmillage = millage + selectedValue;
      this.editForm.get('nextmillage')?.setValue(this.nextmillage);
    } else {
      this.nextmillage = null;
      this.editForm.get('nextmillage')?.setValue(null);
    }
  }

  mapFormToAutojobsinvoice(formValue: any): IAutojobsinvoice {
    return {
      id: formValue.id || null,
      jobid: this.editForm.controls.id.value ?? 0,
      code: formValue.code || '',
      quoteid: formValue.quoteid || null,
      orderid: formValue.orderid || 0,
      autojobsrepid: formValue.autojobsrepid || null,
      autojobsrepname: formValue.autojobsrepname || '',
      delieverfrom: formValue.delieverfrom || '',
      customerid: formValue.customerid,
      customername: formValue.customername || '',
      customeraddress: formValue.customeraddress || '',
      deliveryaddress: formValue.deliveryaddress || '',
      subtotal: (this.totalServiceCharge || 0) + (this.calculateTotalWithDiscount() || 0) + (this.totalcommonServiceCharge || 0),
      totaltax: formValue.totaltax || 0,
      totaldiscount: formValue.totaldiscount || 0,
      nettotal: (this.totalServiceCharge || 0) + (this.calculateTotalWithDiscount() || 0) + (this.totalcommonServiceCharge || 0),
      message: formValue.message || '',
      lmu: formValue.lmu || null,
      paidamount: formValue.paidamount || null,
      amountowing: (this.totalServiceCharge || 0) + (this.calculateTotalWithDiscount() || 0) + (this.totalcommonServiceCharge || 0),
      isactive: formValue.isactive || true,
      locationid: formValue.locationid || 0,
      locationcode: formValue.locationcode || '',
      referencecode: formValue.referencecode || '',
      createdbyid: formValue.createdbyid || null,
      createdbyname: formValue.createdbyname || '',
      autocarecompanyid: formValue.autocarecompanyid || null,
    };
  }

  mapFormTowork(formValue: any): IWorkshopvehiclework {
    return {
      id: formValue.id || null,
      jobid: formValue.jobid || null,
      vehicleid: formValue.vehicleid || null,
      customerid: formValue.customerid || null,
      customername: formValue.customername || '',
      contactno: formValue.contactno || '',
      vehicleno: formValue.vehicleno || '',
      vehiclebrand: formValue.vehiclebrand || '',
      vehiclemodel: formValue.vehiclemodel || '',
      mileage: formValue.mileage || '',
      addeddate: formValue.addeddate ? dayjs(formValue.addeddate) : null,
      iscalltocustomer: formValue.iscalltocustomer || null,
      remarks: formValue.remarks || '',
      calldate: formValue.calldate ? dayjs(formValue.calldate) : null,
      lmu: formValue.lmu || null,
      lmd: formValue.lmd ? dayjs(formValue.lmd) : null,
    };
  }

  printSummary() {
    const printContents = document.getElementById('printSummary')?.innerHTML;
    if (!printContents) {
      console.error('Print section not found!');
      return;
    }

    const printWindow = window.open('', '', 'width=800,height=900');
    if (printWindow) {
      printWindow.document.write(`
        <html>
        <head>
          <title>Print Summary</title>
          <style>
            body { font-family: Arial, sans-serif; padding: 20px; }
            .table { width: 100%; border-collapse: collapse; }
            .table th, .table td { border: 1px solid black; padding: 8px; text-align: left; }
            .text-center { text-align: center; }
            .text-right { text-align: right; }
            .d-flex { display: flex; justify-content: space-between; }
            .mt-3 { margin-top: 20px; }
            .border { border: 1px solid black; padding: 10px; }
          </style>
        </head>
        <body onload="window.print(); window.close();">
          ${printContents}
        </body>
        </html>
      `);
      printWindow.document.close();
    }
  }

  save(): void {
    this.isSaving = true;
    let autocarejob = this.autocarejobFormService.getAutocarejob(this.editForm);

    autocarejob = { ...autocarejob };

    if (autocarejob.id !== null) {
      this.subscribeToSaveResponse(this.autocarejobService.update(autocarejob));
    } else {
      this.subscribeToSaveResponse(this.autocarejobService.create(autocarejob));
    }
  }
  invoid: number = 0;
  saveAll(): void {
    if (this.autojobsinvoiceComponent) {
      this.autojobsinvoiceComponent.save().subscribe({
        next: invoiceIdFromComponent => {
          console.log('Invoice ID returned from autojobsinvoiceComponent.save():', invoiceIdFromComponent);
          // Do something with the invoice ID from the component
          this.invoid = invoiceIdFromComponent;
        },
        error: error => {
          console.error('Component save failed:', error);
        },
      });
    }
    this.save();
    if (this.workshopVehicleWorkListComponent) {
      this.workshopVehicleWorkListComponent.save();
    }
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutocarejob>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: response => {
        if (response.body) {
          const invoiceId = response.body.id; // Extract ID
          console.log('Save response body:', invoiceId);

          // Retrieve stored invoiceId from local storage

          console.log('Retrieved Invoice ID from Local Storage:', this.invoid);

          this.commonServiceArray.forEach(service => {
            service.invoiceid = this.invoid; // Use extracted invoiceId

            // Assuming commonserviceoptionService.create() accepts service data and returns an observable
            this.jobcommon.create({ ...service, id: null }).subscribe({
              next: createResponse => {
                console.log('Serviceee created successfully:', createResponse);
              },
              error: createError => {
                console.error('Error creating service:', createError.body);
              },
            });
          });

          this.serviceArray.forEach(service => {
            service.invoiceid = this.invoid; // Use extracted invoiceId

            // Assuming commonserviceoptionService.create() accepts service data and returns an observable
            this.jobservice.create({ ...service, id: null }).subscribe({
              next: createResponse => {
                console.log('Serviceeeeesssssssssssw created successfully:', createResponse);
              },
              error: createError => {
                console.error('Error creating service:', createError.body);
              },
            });
          });

          // Loop through the itemsArray and update the invoiceId field for each item
          this.itemsArray.forEach((item, index) => {
            setTimeout(() => {
              item.invocieid = this.invoid;

              const itemWithDayjsLmd = {
                ...item,
                id: null,
                lmd: dayjs(item.lmd),
              };

              this.jobinvoicelines.create(itemWithDayjsLmd).subscribe({
                next: createResponse => console.log(`Item ${index + 1} created:`, createResponse),
                error: createError => console.error(`Error for item ${index + 1}:`, createError),
              });
            }, index * 500); // 500ms delay per request
          });

          // console.log('Updated itemsArray:', this.itemsArray);
          // console.log('arryyyyyyyyyyyyyyyyyyyyay service', this.serviceArray);
          this.onSaveSuccess();
        } else {
          console.error('Save response body is null');
        }
      },
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(autocarejob: IAutocarejob): void {
    this.autocarejob = autocarejob;
    this.autocarejobFormService.resetForm(this.editForm, autocarejob);
  }
}
