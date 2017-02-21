package org.pentaho.di.engine.api.remote;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Service representing a single transformation execution on a remote engine.
 * <p>
 * This service serves two purposes. First publishes the original request, to make the execution context available for
 * workers. Second, it functions as a tunnel for events to flow from workers back to the client.
 * <p>
 * This class is parametrized to remove compiler dependencies on event tunneling implementation.
 * `pentaho-object-tunnel` is recommended.
 * <p>
 * Created by hudak on 2/7/17.
 *
 * @param <T> Serialized event type
 */
public interface Execution<T extends Serializable> extends Serializable, AutoCloseable {
  /**
   * @return original request for this execution
   */
  ExecutionRequest getRequest();

  /**
   * Send an event back to the client. Events may be wrapped if additional serialization logic is needed.
   * Usually, these events will be TunneledPayload objects from the pentaho-object-tunnel bundle.
   * <p>
   * This event will be added to the queue to send to the client
   *
   * @param event Serialized event data
   */
  void update( T event );

  /**
   * Open a stream for nosy clients to get live feedback.
   * <p>
   * Create a serialized stream of events sent to this execution via {@link #update(T)}
   * If using the pentaho-object-tunnel, wrap with a TunnelInput
   * <p>
   * Behavior may be non-deterministic if more than one stream is opened
   *
   * @return Serialized stream of events
   */
  InputStream eventStream() throws IOException;
}
