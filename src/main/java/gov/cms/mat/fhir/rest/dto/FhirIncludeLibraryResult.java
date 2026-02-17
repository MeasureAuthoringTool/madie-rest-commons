package gov.cms.mat.fhir.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FhirIncludeLibraryResult {
  String libraryName;
  String libraryVersion;
  boolean outcome;
  Set<FhirIncludeLibraryReferences> libraryReferences = new HashSet<>();
}
