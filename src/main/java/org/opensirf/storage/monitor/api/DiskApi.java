/*
 * OpenSIRF Storage Monitor
 * 
 * Copyright IBM Corporation 2016.
 * All Rights Reserved.
 * 
 * MIT License:
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 * Except as contained in this notice, the name of a copyright holder shall not
 * be used in advertising or otherwise to promote the sale, use or other
 * dealings in this Software without prior written authorization of the
 * copyright holder.
 */

package org.opensirf.storage.monitor.api;

import java.io.File;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.opensirf.storage.monitor.model.StorageMetadata;

/**
 * @author pviana
 *
 */
@Path("sirf")
public class DiskApi {
	@GET
	@Path("storageMetadata")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public StorageMetadata getMetadata(@FormDataParam("storagePath") String storagePath) {
		if(storagePath.equalsIgnoreCase("swift"))
			storagePath = DEFAULT_SWIFT_FILESYSTEM_LOCATION;
		
		File f = new File(storagePath);
		
		float percentageFree = ((float) f.getUsableSpace()) / ((float) f.getTotalSpace());
		float spaceInMegaBytes = ((float) (f.getTotalSpace() / 1024 / 1024));
		return new StorageMetadata(percentageFree, spaceInMegaBytes);
	}
	
	public static final String DEFAULT_SWIFT_FILESYSTEM_LOCATION = "/opt/stack/data";
}
