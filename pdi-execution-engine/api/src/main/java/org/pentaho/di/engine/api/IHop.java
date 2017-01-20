package org.pentaho.di.engine.api;

import org.pentaho.di.engine.api.reporting.ILogicalModelElement;

/**
 * @author nhudak
 */
public interface IHop extends ILogicalModelElement {
  @Override default String getId() {
    return getFrom().getId() + " -> " + getTo().getId();
  }

  String TYPE_NORMAL = "NORMAL";

  IOperation getFrom();

  IOperation getTo();

  default String getType() {
    return TYPE_NORMAL;
  }

}