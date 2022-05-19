import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAisleDiscount } from 'app/shared/model/aisle-discount.model';
import { getEntities } from './aisle-discount.reducer';

export const AisleDiscount = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const aisleDiscountList = useAppSelector(state => state.aisleDiscount.entities);
  const loading = useAppSelector(state => state.aisleDiscount.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="aisle-discount-heading" data-cy="AisleDiscountHeading">
        Aisle Discounts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/aisle-discount/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Aisle Discount
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {aisleDiscountList && aisleDiscountList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Aisle Id</th>
                <th>Product</th>
                <th>Discount</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {aisleDiscountList.map((aisleDiscount, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/aisle-discount/${aisleDiscount.id}`} color="link" size="sm">
                      {aisleDiscount.id}
                    </Button>
                  </td>
                  <td>{aisleDiscount.aisleId}</td>
                  <td>{aisleDiscount.product}</td>
                  <td>{aisleDiscount.discount}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/aisle-discount/${aisleDiscount.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/aisle-discount/${aisleDiscount.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/aisle-discount/${aisleDiscount.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Aisle Discounts found</div>
        )}
      </div>
    </div>
  );
};

export default AisleDiscount;
