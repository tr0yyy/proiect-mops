import { Component, Inject} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { environment } from 'src/environments/environment';
import { ActivatedRoute } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import { RoleEnum } from '../models/role.enum';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  articole: ArticolInterface[] = [];
  domenii: string[] = []
  public articoleFiltrate: ArticolInterface[] = [];

  constructor(http: HttpClient, private datepipe: DatePipe, private authenticationService: AuthenticationService) {
    console.log(environment.apiPath + '/articol')
      http.get<ArticolResponseInterface>(environment.apiPath + '/core/all').subscribe(async result => {
        
        const res = await result;
        this.articole = res.message;
        for (var articol of this.articole) {
          articol.link = this.generateLinkFromTitle(window.location.pathname, articol.titlu)
          articol.dataCreari  = this.datepipe.transform(articol.dataCreari as string, 'dd/MM/yyyy') as string
        }
        this.articoleFiltrate = this.articole;
      }, error => console.error(error));
      http.get<DomeniiResponseInterface>(environment.apiPath + '/core/alldomains').subscribe(async result => {
        
        const res = await result;
        this.domenii = res.message;
        console.log(this.domenii);
      }, error => console.error(error));
  }

  filterValues(value: any) {
    value = value.target.value
    value = value.toLowerCase();
    if (value == "") {
      this.articoleFiltrate = this.articole;
    } else {
      this.articoleFiltrate = this.articoleFiltrate.filter((articol) => articol.titlu.toLowerCase().includes(value))
    }
  }

  generateLinkFromTitle(baseUrl: string, title: string) {
    let link: string = baseUrl + "articol/" + title;
    return link;
  }

  public accessToTable : boolean = this.checkAccessToTable()

  checkAccessToTable() : boolean {
    let value = this.authenticationService.getRoles()?.includes(RoleEnum.Admin)
    if(value === true) {
      return true;
    } else {
      return false;
    }
  }

  generateLinkFromDomain(domain: string) {
    return window.location.href + "articole/" + domain
  }
  
}

interface DomeniiResponseInterface {
  message: Array<string>,
  success: boolean
}

interface ArticolResponseInterface {
  message: Array<ArticolInterface>,
  success: boolean
}

interface ArticolInterface {
  id: number;
  categorie: string;
  titlu: string;
  autor: object;
  dataCreari: string;
  continut: string;
  link: string;
}
