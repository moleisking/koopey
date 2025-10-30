import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from "@angular/core";
import { ControlValueAccessor, FormControl, NgControl } from "@angular/forms";
import { Environment } from "./../../../../environments/environment";
import { MatSelectChange } from "@angular/material/select";

@Component({
      changeDetection: ChangeDetectionStrategy.OnPush  ,
    selector: "filedownload",
      standalone: false,
    styleUrls: ["filedownload.css"],
    templateUrl: "filedownload.html",
})
export class FileDownloadComponent {

    private saveJSONToFile(uri: any, type: string, filename: string) {
        var blob = this.dataURItoBlob(uri),
            e = document.createEvent("MouseEvents"),
            a = document.createElement("a");

        a.download = filename;
        a.href = window.URL.createObjectURL(blob);
        a.dataset.downloadurl = [type, a.download, a.href].join(":");
        e.initMouseEvent(
            "click",
            true,
            false,
            window,
            0,
            0,
            0,
            0,
            0,
            false,
            false,
            false,
            false,
            0,
            null
        );
        a.dispatchEvent(e);
    }

    private dataURItoBlob(dataURI: string): Blob {
        // convert base64/URLEncoded data component to raw binary data held in a string
        if (dataURI.split(",")[0].indexOf("base64") >= 0) {
            var byteString = atob(dataURI.split(",")[1]);

            // separate out the mime component
            var mimeString = dataURI.split(",")[0].split(":")[1].split(";")[0];

            // write the bytes of the string to a typed array
            // if (byteString != null)
            var ia = new Uint8Array(byteString.length);
            for (var i = 0; i < byteString.length; i++) {
                ia[i] = byteString.charCodeAt(i);
            }

            return new Blob([ia], { type: mimeString });
        }
        return new Blob();
    }

    public handleFileDownload() {
        console.log("handleFileDownloadEvent");
      /*  this.assetService.readFile(this.asset.id).subscribe(
            //Json script returned
            (asset) => {
                console.log("handleFileDownloadEvent() start download");
                // this.downloadFile(data);
                this.saveJSONToFile(asset.file.data, asset.file.type, asset.file.name);
            },
            (error) => {
                console.log("handleFileDownloadEvent() error");
                this.alertService.error(<any>error);
            },
            () => {
                console.log("handleFileDownloadEvent() end");
            }
        );*/
    }
}