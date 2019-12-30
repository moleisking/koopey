export class DateHelper {

    public static convertEpochToDateTimeString(epoch: number): string {
        var date = new Date(epoch);
        return date.toLocaleDateString() + " " + date.getHours() + ":" + date.getMinutes();
    }

    public static convertEpochToDateString(epoch: number): string {
        return new Date(epoch).toISOString().split('T')[0];
    }

    public static convertEpochToDate(epoch: number): Date {
        return new Date(epoch);
    }

    public static convertDateToEpoch(date: Date): number {
        return date.getTime();
    }

}
