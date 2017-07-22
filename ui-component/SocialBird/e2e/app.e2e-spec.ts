import { SocialBirdPage } from './app.po';

describe('social-bird App', () => {
  let page: SocialBirdPage;

  beforeEach(() => {
    page = new SocialBirdPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
