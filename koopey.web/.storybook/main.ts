import { StorybookConfig } from '@storybook/angular';

const config: StorybookConfig = {
  stories: [
    '../src/**/*.stories.@(js|jsx|ts|tsx)'
  ],
  addons: [],
  framework: { name: '@storybook/angular', options: { enableIvy: false, } },
  core: {
    'builder': '@storybook/builder-webpack5',
    disableTelemetry: true
  }
};

export default config;