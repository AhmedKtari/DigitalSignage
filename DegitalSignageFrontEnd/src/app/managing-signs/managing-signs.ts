import { Component } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { Authservice } from '../Services/authservice';

interface Media {
  id: number;
  name: string;
  link: string;
  type: string;
  size: number;
};

@Component({
  selector: 'app-managing-signs',
  imports: [RouterLink],
  templateUrl: './managing-signs.html',
  styleUrl: './managing-signs.css',
})

export class ManagingSignsComponent {
  username: string = '';
  ShowCreateSignageContent: boolean = false;
  ShowBrowseSignageContent: boolean = false;
  scheduledSignage: boolean = false;
  showStartfield: boolean = false;
  ShowMedia: boolean = false;
   scheduleMode: 'schedule' | 'immediate' = 'immediate';
  mediaList: Media[] = [];
  selectedMediaId: number | null = null;
  startDate: string = '';
  endDate: string = '';
  status: string = 'offline';

  constructor
    (
        private route: ActivatedRoute,
        private router: Router,
        private authService: Authservice
    ) 
    {
    
  
    }

  ngOnInit() {
    this.username = this.route.snapshot.params['username'];
  }

  async showingMedia(){
    const email = this.authService.getAuthenticatedEmail();
    const response = await fetch(
      `http://localhost:8080/api/media/showMedia?emailRequest=${encodeURIComponent(email || '')}`,
      { method: 'GET' }
    );
    
    const results = await response.json();
    
    if (!response.ok) {
      
      alert(results.message);
      return;
    }
    let list: Media[] = [];
    if (Array.isArray(results)) {
      for (const media of results) {
        list.push({
          id: media.id,
          name: media.name,
          link: media.link,
          type: media.type,
          size: media.size
        });
      }
    }
    this.mediaList = list;
   const tableBody = document.getElementById('mediaTableBody');
  if (tableBody) {
  tableBody.innerHTML = '';
 for (const media of this.mediaList) {
  const row = document.createElement('tr');
  row.style.cursor = 'pointer';
  row.innerHTML = `
    <td class="preview-cell">
      <input type="radio"  name="mediaSelection" value="${media.id}" ${this.selectedMediaId === media.id ? 'checked' : ''}>
      <img src="${media.link}" alt="${media.name}" style="width:56px;height:56px;object-fit:cover;border-radius:6px;" onerror="this.style.display='none'">
    </td>
    <td class="name-cell">${media.name}</td>
    <td><span class="type-badge type-${media.type}">${media.type}</span></td>
    <td class="size-cell">${(media.size / 1024).toFixed(1)} KB</td>
  `;

  
  tableBody.appendChild(row);
}
}
  }
  async uploadMedia() {
    const fileInput = document.getElementById('signageImage') as HTMLInputElement;
    if (!fileInput || !fileInput.files || fileInput.files.length === 0) {
      return;
    }

    const file = fileInput.files[0];
    const formData = new FormData();
    formData.append('file', file);
    formData.append('emailRequest', this.authService.getAuthenticatedEmail() || '');

    const response = await fetch('http://localhost:8080/api/media/MediaUpload', {
      method: 'POST',
      body: formData
    });

    if (!response.ok) {
      alert("Failed to upload media");
      return;
    }

    const result = await response.json();
  }
  async createSign() {
    alert("clicked 1"); 
    const signageTitleInput = document.getElementById('signageTitle') as HTMLInputElement;
    const signageTitle = signageTitleInput.value.trim();
    const startDateInput = document.getElementById('StartingDate') as HTMLInputElement;
    const endDateInput = document.getElementById('EndingDate') as HTMLInputElement;
    const immediateModeInput = document.getElementById('immediateMode') as HTMLInputElement;
    const mediaSelectionInputs = document.getElementsByName('mediaSelection') as NodeListOf<HTMLInputElement>;
    let selectedMediaIdAsString: string | null = null;
    for (const input of mediaSelectionInputs) {
      if (input.checked) {
        selectedMediaIdAsString = input.value;
        alert("Selected Media ID: " + selectedMediaIdAsString);
        break;
      }
    }
    if(this.scheduleMode === 'schedule') {
      this.startDate = startDateInput.value;
  
    } else {
      this.startDate = new Date().toISOString();
      
    }
    this.endDate = endDateInput.value;
    this.selectedMediaId = selectedMediaIdAsString !== null ? parseInt(selectedMediaIdAsString) : null;
  
    if (signageTitle=="") {
      alert("Please enter a signage title");
      return;
    }
    alert("clicked 2"); 
    if (this.selectedMediaId === null) {
      alert("Please select a media");
      return;
    }
    if (this.scheduleMode === 'schedule' && (this.startDate === '' || this.endDate === '')) {
      alert("Please select start and end dates for scheduled signage");
      return;
    }
    if (this.scheduleMode === 'schedule' && this.startDate >= this.endDate) {
      alert("Start date must be before end date");
      return;
    }
    if(this.endDate === ''){
      alert("Please select an end date");
      return;
    }
    if (this.scheduleMode === 'immediate') {
      this.status = 'online';
    }
    
  
    const email = this.authService.getAuthenticatedEmail();
   
         const response = await fetch
(
          'http://localhost:8080/api/sign/createSign',
  {
       method: 'POST',
       headers: { 'Content-Type': 'application/json' 
                
         },
         body: JSON.stringify({ 
                                userEmailRequest: email, 
                                mediaIdRequest: this.selectedMediaId,
                                signTitleRequest: signageTitle ,
                                signStartDateRequest: this.startDate,
                                signEndDateRequest: this.endDate,
                                signstatusRequest: this.status
                              }
                                )
    }
                              )
   

     alert("waiting for response");
      const result = await response.json();
      alert("response received");
      alert("Result: " + JSON.stringify(result));
      alert(result.message);
      alert(result.url);
      return;
    
  }
}