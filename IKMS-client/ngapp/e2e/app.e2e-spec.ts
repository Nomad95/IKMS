import { NgappPage } from './app.po';

describe('ngapp App', () => {
  let page: NgappPage;

  beforeEach(() => {
    page = new NgappPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
