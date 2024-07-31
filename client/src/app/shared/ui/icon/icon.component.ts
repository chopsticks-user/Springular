import {Component, input} from '@angular/core';

@Component({
  selector: 'app-shared-ui-icon',
  standalone: true,
  imports: [],
  templateUrl: './icon.component.html',
  styleUrl: './icon.component.css'
})
export class IconComponent {
  public name = input.required<string>();
}
