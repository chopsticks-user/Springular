import { Injectable } from '@angular/core';
import { Theme, ThemeName, dark, light } from './theme';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private active: Theme = dark;

  constructor() {}

  setTheme(name: ThemeName) {
    switch (name) {
      case 'light': {
        this.active = light;
        break;
      }
      case 'dark': {
        this.active = dark;
        break;
      }
      default: {
        break;
      }
    }

    Object.keys(this.active.properties).forEach((property) => {
      document.documentElement.style.setProperty(
        property,
        this.active.properties[property]
      );
    });
  }
}
