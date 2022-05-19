import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aisle-discount.reducer';

export const AisleDiscountDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const aisleDiscountEntity = useAppSelector(state => state.aisleDiscount.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aisleDiscountDetailsHeading">AisleDiscount</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aisleDiscountEntity.id}</dd>
          <dt>
            <span id="aisleId">Aisle Id</span>
          </dt>
          <dd>{aisleDiscountEntity.aisleId}</dd>
          <dt>
            <span id="product">Product</span>
          </dt>
          <dd>{aisleDiscountEntity.product}</dd>
          <dt>
            <span id="discount">Discount</span>
          </dt>
          <dd>{aisleDiscountEntity.discount}</dd>
        </dl>
        <Button tag={Link} to="/aisle-discount" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aisle-discount/${aisleDiscountEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AisleDiscountDetail;
