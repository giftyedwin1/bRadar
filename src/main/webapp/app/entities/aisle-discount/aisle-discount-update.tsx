import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICustomer } from 'app/shared/model/customer.model';
import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { IAisleDiscount } from 'app/shared/model/aisle-discount.model';
import { getEntity, updateEntity, createEntity, reset } from './aisle-discount.reducer';

export const AisleDiscountUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const customers = useAppSelector(state => state.customer.entities);
  const aisleDiscountEntity = useAppSelector(state => state.aisleDiscount.entity);
  const loading = useAppSelector(state => state.aisleDiscount.loading);
  const updating = useAppSelector(state => state.aisleDiscount.updating);
  const updateSuccess = useAppSelector(state => state.aisleDiscount.updateSuccess);
  const handleClose = () => {
    props.history.push('/aisle-discount');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCustomers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...aisleDiscountEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...aisleDiscountEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="saLaunchApp.aisleDiscount.home.createOrEditLabel" data-cy="AisleDiscountCreateUpdateHeading">
            Create or edit a AisleDiscount
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="aisle-discount-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Aisle Id" id="aisle-discount-aisleId" name="aisleId" data-cy="aisleId" type="text" />
              <ValidatedField label="Product" id="aisle-discount-product" name="product" data-cy="product" type="text" />
              <ValidatedField label="Discount" id="aisle-discount-discount" name="discount" data-cy="discount" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aisle-discount" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AisleDiscountUpdate;
