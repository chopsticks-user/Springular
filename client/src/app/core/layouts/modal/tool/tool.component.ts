import {Component, input, output} from '@angular/core';
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-layout-modal-tool',
  standalone: true,
  imports: [
    MatIcon
  ],
  templateUrl: './tool.component.html',
  styleUrl: './tool.component.css'
})
export class ToolComponent {
  public name = input<string>();
  public icon = input.required<string>();
  public click = output<void>();
}
