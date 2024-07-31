import {Component, input, output} from '@angular/core';
import {IconComponent} from "@shared/ui/icon/icon.component";

@Component({
  selector: 'app-layout-dialog-modal-tool',
  standalone: true,
  imports: [
    IconComponent
  ],
  templateUrl: './tool.component.html',
  styleUrl: './tool.component.css'
})
export class ToolComponent {
  public icon = input.required<string>();
  public click = output<void>();
}
