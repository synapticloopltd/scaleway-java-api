/**
 * This provides the classes necessary to serialise the responses from the API 
 * calls.
 * 
 * All files <strong>MUST</strong> take the form of <em>*Response.java</em> 
 * which are then used by Jackson to deserialise the information.
 * 
 * All response classes then deserialise the returned JSON string into the
 * various models that are available.
 * 
 * Any classes that provide a paginated response, that is any call to a
 * <strong>getAll*()</strong> methods inherit from the <em>BasePaginationResponse</em>
 * 
 * @see synapticloop.scaleway.api.response.BasePaginationResponse for methods 
 * that this base class provides for information about the current page, total
 * number of pages and number of results per page
 * 
 */
package synapticloop.scaleway.api.response;