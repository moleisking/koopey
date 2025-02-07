import { MatSnackBar } from '@angular/material/snack-bar';
import { Meta, moduleMetadata, StoryObj } from '@storybook/angular';
import { AppModule } from '../../../components/application.module';
import { AlertService } from '../../../services/alert.service';
import { StorybookTranslateModule } from './StorybookTranslateModule';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { HttpClientModule } from '@angular/common/http';
import { MessageService } from 'src/app/services/message.service';
import { UserService } from 'src/app/services/user.service';
import { DashboardComponent } from 'src/app/components/dashboard/dashboard.component';

const meta: Meta<DashboardComponent> = {
  component: DashboardComponent,
  decorators: [
    moduleMetadata({
      imports: [StorybookTranslateModule,HttpClientModule],
      declarations: [],
      providers: [AlertService, AuthenticationService, MatSnackBar, MessageService , UserService],
    }),
  ],
};
export default meta;

type Story = StoryObj<DashboardComponent>;

export const Empty: Story = {
  decorators: [
    moduleMetadata({
      imports: [],
      declarations: [],
      providers: [],
    }),
  ],
};

export const Complete: Story = {
  decorators: [
    moduleMetadata({
      imports: [],
      declarations: [],
      providers: [],
    }),
  ],
};