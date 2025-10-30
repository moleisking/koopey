import { StorybookConfig } from '@storybook/angular';

const config: StorybookConfig = {
  stories: [
    '../src/**/*.stories.@(js|jsx|ts|tsx)'
  ],
  addons: [],
  framework: { name: '@storybook/angular', options: { enableIvy: true, } },
};

export default config;