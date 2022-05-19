import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AisleDiscount from './aisle-discount';
import AisleDiscountDetail from './aisle-discount-detail';
import AisleDiscountUpdate from './aisle-discount-update';
import AisleDiscountDeleteDialog from './aisle-discount-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AisleDiscountUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AisleDiscountUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AisleDiscountDetail} />
      <ErrorBoundaryRoute path={match.url} component={AisleDiscount} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AisleDiscountDeleteDialog} />
  </>
);

export default Routes;
