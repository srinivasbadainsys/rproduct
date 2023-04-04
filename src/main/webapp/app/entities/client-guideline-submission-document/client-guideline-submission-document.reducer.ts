import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IClientGuidelineSubmissionDocument, defaultValue } from 'app/shared/model/client-guideline-submission-document.model';

const initialState: EntityState<IClientGuidelineSubmissionDocument> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/client-guideline-submission-documents';

// Actions

export const getEntities = createAsyncThunk(
  'clientGuidelineSubmissionDocument/fetch_entity_list',
  async ({ page, size, sort }: IQueryParams) => {
    const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;
    return axios.get<IClientGuidelineSubmissionDocument[]>(requestUrl);
  }
);

export const getEntity = createAsyncThunk(
  'clientGuidelineSubmissionDocument/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IClientGuidelineSubmissionDocument>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'clientGuidelineSubmissionDocument/create_entity',
  async (entity: IClientGuidelineSubmissionDocument, thunkAPI) => {
    const result = await axios.post<IClientGuidelineSubmissionDocument>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'clientGuidelineSubmissionDocument/update_entity',
  async (entity: IClientGuidelineSubmissionDocument, thunkAPI) => {
    const result = await axios.put<IClientGuidelineSubmissionDocument>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'clientGuidelineSubmissionDocument/partial_update_entity',
  async (entity: IClientGuidelineSubmissionDocument, thunkAPI) => {
    const result = await axios.patch<IClientGuidelineSubmissionDocument>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'clientGuidelineSubmissionDocument/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IClientGuidelineSubmissionDocument>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const ClientGuidelineSubmissionDocumentSlice = createEntitySlice({
  name: 'clientGuidelineSubmissionDocument',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = ClientGuidelineSubmissionDocumentSlice.actions;

// Reducer
export default ClientGuidelineSubmissionDocumentSlice.reducer;
