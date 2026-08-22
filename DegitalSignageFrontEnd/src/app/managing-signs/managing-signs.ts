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
  ShowMedia: boolean = false;
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
    alert("clicked");
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
      <img src="${media.link}" alt="${media.name}" style="width:56px;height:56px;object-fit:cover;border-radius:6px;" onerror="this.style.display='none'">
    </td>
    <td class="name-cell">${media.name}</td>
    <td><span class="type-badge type-${media.type}">${media.type}</span></td>
    <td class="size-cell">${(media.size / 1024).toFixed(1)} KB</td>
  `;

  row.addEventListener('click', () => this.rowClicked(media.id));
  tableBody.appendChild(row);
}
}
  }
  rowClicked(mediaId: number) {
    this.selectedMediaId = mediaId;
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
    const signageNameInput = document.getElementById('signageName') as HTMLInputElement;
    const signageName = signageNameInput.value.trim();
    const startDateInput = document.getElementById('StartingDate') as HTMLInputElement;
    const endDateInput = document.getElementById('EndingDate') as HTMLInputElement;
    const immediateModeInput = document.getElementById('immediateMode') as HTMLInputElement;
    this.startDate = startDateInput.value;
    this.endDate = endDateInput.value;
    if (!signageName) {
      alert("Please enter a signage name");
      return;
    }
    if (this.selectedMediaId === null) {
      alert("Please select a media");
      return;
    }
    if (immediateModeInput.checked) {
      this.status = "online";
      
      }

    const email = this.authService.getAuthenticatedEmail();
   
         const response = await fetch
(
          'http://localhost:8080/api/auth/sign/createSign',
  {
       method: 'POST',
       headers: { 'Content-Type': 'application/json' 
                
         },
         body: JSON.stringify({ 
                                userEmailRequest: email, 
                                mediaIdRequest: this.selectedMediaId,
                                signNameRequest: signageName ,
                                signStartDateRequest: this.startDate,
                                signEndDateRequest: this.endDate,
                                signstatusRequest: this.status
                              }
                                )
   }
                              )
   

    
    if (!response.ok) {
      const result = await response.json();
      alert(result.message);
      return;
    }
  }
}