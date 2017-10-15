/* SystemJS module definition */
declare var module: NodeModule;
interface NodeModule {
  id: string;
}

//TODO: jesli aplikacja zacznie działać powoli zamienimy wildcarda na konkret
declare module "*.json" {
    const value: any;
    export default value;
}
