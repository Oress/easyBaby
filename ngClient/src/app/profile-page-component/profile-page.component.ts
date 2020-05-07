import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { Store } from '@ngrx/store';
import { AppState } from '../store/AppState';
import { selectUserData } from '../store/appSelectors';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ContactInfo } from '../model/contactInfo';
import { UserWebService } from '../services/web/user-web.service';
import { SetUserData } from '../store/appActions';

@Component({
  selector: 'app-profile-page-component',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.scss']
})
export class ProfilePageComponent implements OnInit {
  user: User;

  edit = false;
  contactInfoForm: FormGroup;

  maxDateValue: Date = new Date();
  yearRange: string = '1900:' + this.maxDateValue.getFullYear();

  constructor(
    private store: Store<AppState>,
    private userService: UserWebService,
    private fb: FormBuilder,
  ) {
  }

  ngOnInit(): void {
    this.contactInfoForm = this.fb.group({
      username: ['', [Validators.required]],
      fname: ['', [Validators.required, Validators.minLength(3)]],
      lname: ['', [Validators.required, Validators.minLength(3)]],
      mname: [''],
      email: ['', [Validators.email]],
      dob: [undefined],
      mainPhone: ['', [Validators.required]],
      addPhone: [''],
    });

    this.contactInfoForm.disable();

    this.store.select(selectUserData).subscribe(data => {
      if (data) {
        this.user = data;

        this.contactInfoForm.controls.username.setValue(this.user.username);
        this.contactInfoForm.controls.fname.setValue(this.user.contactInfo.firstName);
        this.contactInfoForm.controls.lname.setValue(this.user.contactInfo.lastName);
        this.contactInfoForm.controls.mname.setValue(this.user.contactInfo.middleName);
        this.contactInfoForm.controls.email.setValue(this.user.contactInfo.email);
        this.contactInfoForm.controls.dob.setValue(new Date(this.user.contactInfo.dob));
        this.contactInfoForm.controls.mainPhone.setValue(this.user.contactInfo.phone1);
        this.contactInfoForm.controls.addPhone.setValue(this.user.contactInfo.phone2);
      }
    });
  }

  getName(user: User) {
    let name = '';
    if (user) {
      name = `${user.contactInfo.lastName} ${user.contactInfo.firstName} ${user.contactInfo.middleName}`;
    }
    return name;
  }

  toggleEdit() {
    // this.contactInfoForm.disable();
    this.edit = !this.edit;

    if (this.edit) {
      this.contactInfoForm.enable();
      this.contactInfoForm.controls.username.disable();
    } else {
      this.contactInfoForm.disable();
    }
  }


  save() {
    if (this.contactInfoForm.valid) {
      this.toggleEdit();
      const newData: User = {};
      const contactInfo: ContactInfo = {};

      newData.id = this.user.id;
      newData.username = this.contactInfoForm.controls.username.value;

      contactInfo.firstName = this.contactInfoForm.controls.fname.value;
      contactInfo.lastName = this.contactInfoForm.controls.lname.value;
      contactInfo.middleName = this.contactInfoForm.controls.mname.value;
      contactInfo.email = this.contactInfoForm.controls.email.value;
      contactInfo.phone1 = this.contactInfoForm.controls.mainPhone.value;
      contactInfo.phone2 = this.contactInfoForm.controls.addPhone.value;
      contactInfo.dob = this.contactInfoForm.controls.dob.value;

      newData.contactInfo = contactInfo;

      this.userService.updateData(newData).subscribe(res => {
        if (res) {
          // this.store.dispatch(SetUserData)
          location.reload();
        }
      });
    }
  }
}
