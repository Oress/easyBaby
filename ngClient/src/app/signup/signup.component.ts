import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent implements OnInit {
  @Output() succsessfulLogin: EventEmitter<any> = new EventEmitter();
  public registration = false;
  public username: string;
  public pwd: string;

  public regForm: FormGroup = new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.email]),
    username: new FormControl(null, [Validators.required, Validators.minLength(3)]),
    phone: new FormControl(null, Validators.required),
    pwd: new FormControl(null, Validators.required),
    pwdConf: new FormControl(null, Validators.required),
  }, { validators: [this.checkPasswords], updateOn: 'blur' });

  signUpResult = null;
  loading = false;
  lastLoginAttempt = false;

  constructor(private authService: AuthService) {
  }

  ngOnInit() {
  }

  public signin() {
    console.log(this.username, this.pwd);
    this.authService.signin(this.username, this.pwd)
      .then(res => {
        console.log('closing');
        this.succsessfulLogin.emit(res);
      }, err => {
        this.lastLoginAttempt = true;
      });
  }

  public signUp() {
    const dto = {
      username: this.regForm.controls.username.value,
      pwd: this.regForm.controls.pwd.value,
      phone: this.regForm.controls.phone.value,
      email: this.regForm.controls.email.value,
    };
    console.log(dto);

    this.loading = true;
    this.authService.signUp(dto).subscribe(result => {
      console.log(result);
      this.signUpResult = result;
      this.loading = false;

      this.regForm.reset();
      Object.keys(this.regForm.controls).forEach(key => {
        this.regForm.get(key).setErrors(null);
      });

      setTimeout(() => {
        this.signUpResult = null;
        this.registration = false;
      }, 3000);
    });
  }

  checkPasswords(group: FormGroup) { // here we have the 'passwords' group
    let pass = group.get('pwd').value;
    let confirmPass = group.get('pwdConf').value;

    return pass === confirmPass ? null : { notSame: true }
  }
}
