import { Injectable } from '@angular/core';
import appEnv from '@environments';

@Injectable({
  providedIn: 'root',
})
export class ApiRouteService {
  private _rootAddr: string = this._dropSuffixSlashes(appEnv.apiRootAddr);

  route(relativePath: string): string {
    return `${this._rootAddr}/${this._dropPrefixSlashes(relativePath)}`;
  }

  private _dropPrefixSlashes(path: string): string {
    return path.replace(/^\/+/, '');
  }

  private _dropSuffixSlashes(path: string): string {
    return path.replace(/\/+$/, '');
  }
}
